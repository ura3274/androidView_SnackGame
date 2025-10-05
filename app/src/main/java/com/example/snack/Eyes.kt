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

class Eyes(val food: Food, inPos1: Point, inPos2: Point) {
    var pos1X = inPos1.x
    var pos1Y = inPos1.y
    var pos2X = inPos2.x
    var pos2Y = inPos2.y
    val radius = 10.0
    val pupPos1 = Point(pos1X, pos1Y)
    val pupPos2 = Point(pos2X, pos2Y)
    val pupilRadius = 3
    var up = true
    var angl = 0
    var tm = System.currentTimeMillis()
    val eyePosOffset = 10

//==================================================================================================
    fun movePupLid(pos1:Point, pos2:Point, dir:String){
        when(dir){
            "r"->{
                pos1X = pos1.x - eyePosOffset
                pos1Y = pos1.y
                pos2X = pos2.x - eyePosOffset
                pos2Y = pos2.y
            }
            "l"->{
                pos1X = pos1.x + eyePosOffset
                pos1Y = pos1.y
                pos2X = pos2.x + eyePosOffset
                pos2Y = pos2.y
            }
            "d"->{
                pos1X = pos1.x
                pos1Y = pos1.y - eyePosOffset
                pos2X = pos2.x
                pos2Y = pos2.y - eyePosOffset
            }
            "u"->{
                pos1X = pos1.x
                pos1Y = pos1.y + eyePosOffset
                pos2X = pos2.x
                pos2Y = pos2.y + eyePosOffset
            }
        }

        pupPos1.x = ((radius/2) * cos(atan2((food.posY - pos1Y).toDouble(), (food.posX - pos1X).toDouble()))).toInt()+pos1X
        pupPos1.y = ((radius/2) * sin(atan2((food.posY - pos1Y).toDouble(), (food.posX - pos1X).toDouble()))).toInt()+pos1Y
        pupPos2.x = ((radius/2) * cos(atan2((food.posY - pos2Y).toDouble(), (food.posX - pos2X).toDouble()))).toInt()+pos2X
        pupPos2.y = ((radius/2) * sin(atan2((food.posY - pos2Y).toDouble(), (food.posX - pos2X).toDouble()))).toInt()+pos2Y
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
        canvas.drawCircle(pos1X.toFloat(), pos1Y.toFloat(), radius.toFloat(), paint)
        canvas.drawCircle(pos2X.toFloat(), pos2Y.toFloat(), radius.toFloat(), paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.BLACK
        canvas.drawCircle(pos1X.toFloat(), pos1Y.toFloat(), radius.toFloat(), paint)
        canvas.drawCircle(pos2X.toFloat(), pos2Y.toFloat(), radius.toFloat(), paint)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(pupPos1.x.toFloat(), pupPos1.y.toFloat(), pupilRadius.toFloat(), paint)
        canvas.drawCircle(pupPos2.x.toFloat(), pupPos2.y.toFloat(), pupilRadius.toFloat(), paint)
        paint.color = Color.GREEN
        val rectHord1 = RectF((pos1X).toFloat() - (radius).toFloat(), (pos1Y-radius).toFloat(), (pos1X).toFloat() + (radius).toFloat(), (pos1Y+radius).toFloat())
        val rectHord2 = RectF((pos2X).toFloat() - (radius).toFloat(), (pos2Y-radius).toFloat(), (pos2X).toFloat() + (radius).toFloat(), (pos2Y+radius).toFloat())
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