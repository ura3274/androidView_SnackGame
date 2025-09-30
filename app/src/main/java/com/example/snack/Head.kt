package com.example.snack

import android.graphics.Point
import android.graphics.RectF
import android.util.Log

class Head(inPos:Point){
    val width:Int = 10
    var posUnder:Point
    var posBelow:Point

    var dir:String = "r"
        set(value){
            when(value){
                "r" ->{
                    //posX += width
                    posUnder.x = posX
                    posUnder.y = posY-width
                    posBelow.x = posX
                    posBelow.y = posY+width
                }
                "l" ->{
                    //posX -= width
                    posUnder.x = posX
                    posUnder.y = posY+width
                    posBelow.x = posX
                    posBelow.y = posY-width
                }
                "d" ->{
                    //posY += width
                    posUnder.x = posX+width
                    posUnder.y = posY
                    posBelow.x = posX-width
                    posBelow.y = posY
                }
                "u" ->{
                    //posY -= width
                    posUnder.x = posX-width
                    posUnder.y = posY
                    posBelow.x = posX+width
                    posBelow.y = posY
                }
            }
            field = value
        }

    var posX:Int = inPos.x
        set(value){
            field = value
            posUnder.x = value
            posBelow.x = value
        }
    var posY:Int = inPos.y
        set(value){
            field = value
            posUnder.y = value
            posBelow.y = value
        }
    //val pts:Array<Point> = arrayOf(Point(pos.x, pos.y-2), Point(pos.x, pos.y+2))

    init {
        posUnder = Point(posX, posY - width)
        posBelow = Point(posX, posY + width)
    }

//=======================================================================================================================

    fun headArc():Float{
        when(dir) {
            "r" -> return 270f
            "l" -> return 90f
            "d" -> return 0f
            "u" -> return 180f
        }
        return  270f
    }

}
