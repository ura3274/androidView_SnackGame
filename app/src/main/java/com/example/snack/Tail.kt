package com.example.snack

import android.graphics.Point

class Tail(inPos:Point, inUnder:Point, inBelow:Point, inWidth:Int, inDir:String){
    var dir:String = inDir
    val width:Int = inWidth
    var posUnder:Point = inUnder
    var posBelow:Point = inBelow

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
//==================================================================================================

    fun tailArc():Float{
        when(dir) {
            "r" -> return 90f
            "l" -> return 270f
            "d" -> return 180f
            "u" -> return 0f
        }
        return  270f
    }
    //val pts:Array<Point> = arrayOf(Point(pos.x, pos.y-2), Point(pos.x, pos.y+2))
    /*init {
        when(dir){
            "r","l" ->{
                posUnder = Point(posX, posY - width)
                posBelow = Point(posX, posY + width)
            }
            "d","u" ->{
                posUnder = Point(posX + width, posY)
                posBelow = Point(posX - width, posY)
            }
        }

    }*/
    //val pts:Array<Point> = arrayOf(Point(pos.x, pos.y-2), Point(pos.x, pos.y+2))

}
