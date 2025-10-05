package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import kotlin.random.Random

class Tongue() {
    var startPos:Point = Point(0,0)
    var endPosX = 0
    var endPosY = 0

    fun moveTongue(pos: Point, dir:String){
        startPos = pos
        when(dir){
            "r"-> {
                endPosX = Random.nextInt(from = pos.x+35, until = pos.x+50)
                endPosY = Random.nextInt(from = pos.y-10, until = pos.y+10)
            }
            "l"-> {
                endPosX = Random.nextInt(from = pos.x-50, until = pos.x-35)
                endPosY = Random.nextInt(from = pos.y-10, until = pos.y+10)
            }
            "d"-> {
                endPosX = Random.nextInt(from = pos.x-10, until = pos.x+10)
                endPosY = Random.nextInt(from = pos.y+35, until = pos.y+50)
            }
            "u"-> {
                endPosX = Random.nextInt(from = pos.x-10, until = pos.x+10)
                endPosY = Random.nextInt(from = pos.y-50, until = pos.y-35)
            }
            else ->{}
        }

    }
    fun drawTongue(canvas: Canvas, paint: Paint){
        paint.color = Color.RED
        paint.strokeWidth = 2f

        canvas.drawLine(startPos.x.toFloat(), startPos.y.toFloat(), endPosX.toFloat(), endPosY.toFloat(), paint )
    }
}