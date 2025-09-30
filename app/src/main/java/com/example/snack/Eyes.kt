package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import java.lang.Math.pow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Eyes(val food: Food, inPos1: Point = Point(), inPos2: Point = Point()) {
    var pos1 = inPos1
        set(value){
            field = value
            //pupPos1.x = ((radius/2) * cos(atan2((food.posY - value.y).toDouble(), (food.posX - value.x).toDouble())).toInt())+value.x
            //pupPos1.y = ((radius/2) * sin(atan2((food.posY - value.y).toDouble(), (food.posX - value.x).toDouble())).toInt())+value.y
        }
    var pos2 = inPos2
        set(value){
            field = value
            //pupPos2.x = (pupilRadius * cos(atan2((food.posY - value.y).toDouble(), (food.posX - value.x).toDouble())).toInt())+value.x
            //pupPos2.y = (pupilRadius * sin(atan2((food.posY - value.y).toDouble(), (food.posX - value.x).toDouble())).toInt())+value.y
        }
    val radius = 10.0
    val pupPos1 = Point(pos1.x, pos1.y)
    val pupPos2 = Point(pos2.x, pos2.y)
    val pupilRadius = 3
    var up = true
    var angl = 0
    var tm = System.currentTimeMillis()

//==================================================================================================
    fun movePupLid(){
        pupPos1.x = ((radius/2) * cos(atan2((food.posY - pos1.y).toDouble(), (food.posX - pos1.x).toDouble()))).toInt()+pos1.x
        pupPos1.y = ((radius/2) * sin(atan2((food.posY - pos1.y).toDouble(), (food.posX - pos1.x).toDouble()))).toInt()+pos1.y
        pupPos2.x = ((radius/2) * cos(atan2((food.posY - pos2.y).toDouble(), (food.posX - pos2.x).toDouble()))).toInt()+pos2.x
        pupPos2.y = ((radius/2) * sin(atan2((food.posY - pos2.y).toDouble(), (food.posX - pos2.x).toDouble()))).toInt()+pos2.y
        if(System.currentTimeMillis() - tm > 5000){
            if(up){
                angl += 5
                if(angl == 90)up = false
            }else{
                angl -= 5
                if(angl == 0){
                    up = true
                    tm = System.currentTimeMillis()
                }
            }
        }
    }
    fun drawEyes(canvas: Canvas, paint: Paint, headDir: String){
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawCircle(pos1.x.toFloat(), pos1.y.toFloat(), radius.toFloat(), paint)
        canvas.drawCircle(pos2.x.toFloat(), pos2.y.toFloat(), radius.toFloat(), paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.BLACK
        canvas.drawCircle(pos1.x.toFloat(), pos1.y.toFloat(), radius.toFloat(), paint)
        canvas.drawCircle(pos2.x.toFloat(), pos2.y.toFloat(), radius.toFloat(), paint)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(pupPos1.x.toFloat(), pupPos1.y.toFloat(), pupilRadius.toFloat(), paint)
        canvas.drawCircle(pupPos2.x.toFloat(), pupPos2.y.toFloat(), pupilRadius.toFloat(), paint)
        paint.color = Color.GREEN
        val rectHord1 = RectF((pos1.x).toFloat() - (radius).toFloat(), (pos1.y-radius).toFloat(), (pos1.x).toFloat() + (radius).toFloat(), (pos1.y+radius).toFloat())
        val rectHord2 = RectF((pos2.x).toFloat() - (radius).toFloat(), (pos2.y-radius).toFloat(), (pos2.x).toFloat() + (radius).toFloat(), (pos2.y+radius).toFloat())
        //val path = Path()
        //path.moveTo((pos1.x).toFloat() - (hord/2).toFloat(), (pos1.y - 5).toFloat())
        //path.arcTo(rectHord, (270-angl).toFloat(), (angl*2).toFloat(), true)
        //path.close()
        //canvas.drawPath(path, paint)
        when(headDir){
            "l","r" ->{
                canvas.drawArc(rectHord1, (180-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord1, (360-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord2, (180-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord2, (360-angl).toFloat(), (angl*2).toFloat(), false, paint)
            }
            "d","u" ->{
                canvas.drawArc(rectHord1, (270-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord1, (90-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord2, (270-angl).toFloat(), (angl*2).toFloat(), false, paint)
                canvas.drawArc(rectHord2, (90-angl).toFloat(), (angl*2).toFloat(), false, paint)
            }
        }

    }

}