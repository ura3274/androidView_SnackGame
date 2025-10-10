package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
//import android.util.Log
import kotlin.random.Random

class Food(inPos: Point) {
    var posX = inPos.x
    var posY = inPos.y
    val width:Int = 15
    var enableToDraw = true

//==================================================================================================
    fun foodMove(chart: Chart){
        var flag = false
        while(true){
            posX = Random.nextInt(from = 100, until = chart.chart.size - 100)
            posY = Random.nextInt(from = 100, until = chart.chart[0].size - 100)
            if(chart.chart[posX][posY] == 1) continue
            for(n in 1..25){
                if(chart.chart[posX+n][posY] == 1){
                    //Log.d("myLog", "enter")
                    flag = false
                    break
                }else flag = true
                if(chart.chart[posX-n][posY] == 1){
                    //Log.d("myLog", "enter")
                    flag = false
                    break
                }else flag = true
                if(chart.chart[posX][posY+n] == 1){
                    flag = false
                    break
                }else flag = true
                if(chart.chart[posX][posY-n] == 1){
                    flag = false
                    break
                }else flag = true
            }
            if(flag)break
        }

    }

//==================================================================================================

    fun foodDraw(canvas: Canvas, paint: Paint){
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas.drawCircle(posX.toFloat(), posY.toFloat(), width.toFloat(), paint)
        //paint.style = Paint.Style.STROKE
        //canvas.drawCircle(posX.toFloat(), posY.toFloat(), width+50.toFloat(), paint)
    }
}