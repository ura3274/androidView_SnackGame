package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point

class Food(inPos: Point) {
    var posX = inPos.x
    var posY = inPos.y
    val width:Int = 15

//==================================================================================================
    fun foodMove(){
        posX += 0
        posY += 0
    }

//==================================================================================================

    fun foodDraw(canvas: Canvas, paint: Paint){
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas.drawCircle(posX.toFloat(), posY.toFloat(), width.toFloat(), paint)
    }
}