package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import kotlin.text.toFloat

class Mouth() {
    var posX = 0.0f
    var posY = 0.0f
    var pos1X = 0.0f
    var pos1Y = 0.0f
    var pos2X = 0.0f
    var pos2Y = 0.0f
    var direction = "r"
    var width = 0f

    fun moveMouth(pos: Point,width:Int, dir:String){
        direction = dir
        this.width = width.toFloat()
        posX = pos.x.toFloat()
        posY = pos.y.toFloat()
        when(dir){
            "r", "l"->{
                pos1X = pos.x.toFloat()
                pos1Y = (pos.y+width).toFloat()
                pos2X = pos.x.toFloat()
                pos2Y = (pos.y-width).toFloat()

            }
            "d","u"->{
                pos1X = (pos.x-width).toFloat()
                pos1Y = pos.y.toFloat()
                pos2X = (pos.x+width).toFloat()
                pos2Y = pos.y.toFloat()
            }
        }
    }
    fun drawMouth(canvas: Canvas, paint: Paint){
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
        val rectBend = RectF(posX-width, posY-width, posX+width, posY+width)
        val path = Path()
        path.moveTo(pos1X, pos1Y)
        path.lineTo(pos2X, pos2Y)
        when(direction){
            "r"-> path.arcTo(rectBend, 270f, 180f)
            "l"-> path.arcTo(rectBend, 90f, 180f)
            "d"-> path.arcTo(rectBend, 0f, 180f)
            "u"-> path.arcTo(rectBend, 180f, 180f)
        }
        path.close()
        canvas.drawPath(path, paint)
        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        //paint.strokeWidth = 1f
        when(direction){
            "r"->{
                path.moveTo(posX, posY-2f)
                path.lineTo(posX+5f, posY-5f)
                path.lineTo(posX, posY-10f)
                path.close()
                path.moveTo(posX, posY+2f)
                path.lineTo(posX+5f, posY+5f)
                path.lineTo(posX, posY+10f)
                path.close()
                //canvas.drawLines(floatArrayOf(posX, posY, posX+5f, posY-5f,posX+5f, posY-5f, posX, posY-10f, posX, posY-10f, posX,posY), paint)
            }
            "l"->{
                path.moveTo(posX, posY-2f)
                path.lineTo(posX-5f, posY-5f)
                path.lineTo(posX, posY-10f)
                path.close()
                path.moveTo(posX, posY+2f)
                path.lineTo(posX-5f, posY+5f)
                path.lineTo(posX, posY+10f)
                path.close()
            }
            "d"->{
                path.moveTo(posX-2f, posY)
                path.lineTo(posX-5f, posY+5f)
                path.lineTo(posX-10f, posY)
                path.close()
                path.moveTo(posX+2f, posY)
                path.lineTo(posX+5f, posY+5f)
                path.lineTo(posX+10f, posY)
                path.close()
            }
            "u"->{
                path.moveTo(posX-2f, posY)
                path.lineTo(posX-5f, posY-5f)
                path.lineTo(posX-10f, posY)
                path.close()
                path.moveTo(posX+2f, posY)
                path.lineTo(posX+5f, posY-5f)
                path.lineTo(posX+10f, posY)
                path.close()
            }
        }
        canvas.drawPath(path, paint)


    }
}