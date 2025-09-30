package com.example.snack

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast

import androidx.core.os.HandlerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GameSurfaceView @JvmOverloads constructor (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {
    private var thread: GameThread? = null
    private val chart = Chart()
    private val food:Food = Food(Point(250,300))
    private val snack: Snack = Snack(food)
    private var svCallBack:OnFinishGameCallBack? = null

    init {
        holder.addCallback(this)
        thread = GameThread(holder)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread?.running = true
        thread?.start()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //stopGame()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    inner class GameThread(private val surfaceHolder: SurfaceHolder) : Thread() {
        var running = false

        override fun run() {
            val paint = Paint()
            while (running) {
                val canvas = surfaceHolder.lockCanvas()
                if (canvas != null) {
                    synchronized(surfaceHolder) {
                        // Обновление логики игры
                        // Рисование на Canvas
                        canvas.drawColor(Color.BLACK) // Очистка экрана
                        if(snack.snackMove(chart)){
                            running = false
                        }

                        snack.snackDraw(canvas, paint)
                        food.foodDraw(canvas, paint)
                        //canvas.drawCircle(100f, 100f, 50f, paint)
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
            HandlerCompat.postDelayed(
                Handler(Looper.getMainLooper()),
                { stopGame() },
                null,
                50
            )
        }
    }

    interface OnFinishGameCallBack{
        fun finishGame()
    }

    fun setCallBack(callback:OnFinishGameCallBack){
        svCallBack = callback
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                if(event.y > snack.snHead.posY){
                    if(snack.snHead.dir != "d" && snack.snHead.dir != "u"){
                        snack.turn = true
                        snack.dir = "d"
                    }
                }
                if(event.y < snack.snHead.posY){
                    if(snack.snHead.dir != "d" && snack.snHead.dir != "u"){
                        snack.turn = true
                        snack.dir = "u"
                    }
                }
                if(event.x < snack.snHead.posX){
                    if(snack.snHead.dir != "r" && snack.snHead.dir != "l"){
                        snack.turn = true
                        snack.dir = "l"
                    }
                }
                if(event.x > snack.snHead.posX){
                    if(snack.snHead.dir != "r" && snack.snHead.dir != "l"){
                        snack.turn = true
                        snack.dir = "r"
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // Логика перемещения
            }
        }
        return super.onTouchEvent(event)
    }

    fun stopGame(){
        //var retry = true
        thread?.running = false
        //while (retry) {
            try {
                thread?.join()
                //retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        //}
        thread = null
        svCallBack?.finishGame()
    }
}