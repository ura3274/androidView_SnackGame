package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Snack(val food:Food) {

    var dir:String = "r"
    var turn:Boolean = false
    val snHead = Head(inPos = Point(100, 100))
    val snTail:MutableList<Tail> = mutableListOf(Tail(inPos = Point(20, 100), Point(20, 90), Point(20, 110), 10, "r"))
    val eyes = Eyes(food, snHead.posUnder, snHead.posBelow)
    val snMove:Float = 2.0f
    var isEating:Boolean = false
    var bodyGrow:Int = 0


//=====================================================================================================

    fun snackMove(chart: Chart):Boolean{
        if(chart.chart[if(snHead.posX < chart.chart.size)snHead.posX else chart.chart.size-1][if(snHead.posY < chart.chart[0].size)snHead.posY else chart.chart[0].size-1] == 1)return true
        chart.chart[snHead.posX][snHead.posY] = 1

        when(dir){
            "r" ->{
                if(turn){
                    //val dr = snTail[snTail.lastIndex].dir
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    snHead.dir = "r"
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    turn = false
                }
                for(n in 1.. snMove.toInt()){
                    chart.chart[snHead.posX - n][snHead.posY] = 1
                }
                snHead.posX += snMove.toInt()
            }
            "l" ->{
                if(turn){
                    //val dr = snTail[snTail.lastIndex].dir
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    snHead.dir = "l"
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    turn = false
                }
                for(n in 1.. snMove.toInt()){
                    chart.chart[snHead.posX + n][snHead.posY] = 1
                }
                snHead.posX -= snMove.toInt()
            }
            "d" ->{
                if(turn){
                    //val dr = snTail[snTail.lastIndex].dir
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    snHead.dir = "d"
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    turn = false
                    //Log.d("myLog", "${snTail.size}")
                }
                for(n in 1.. snMove.toInt()){
                    chart.chart[snHead.posX][snHead.posY - n] = 1
                }
                snHead.posY += snMove.toInt()
            }
            "u" ->{
                if(turn){
                    //val dr = snTail[snTail.lastIndex].dir
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    snHead.dir = "u"
                    snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                    turn = false
                }
                for(n in 1.. snMove.toInt()){
                    chart.chart[snHead.posX][snHead.posY + n] = 1
                }
                snHead.posY -= snMove.toInt()
            }
        }

        //eyes.pos1 = snHead.posUnder
        //eyes.pos2 = snHead.posBelow

        eyes.movePupLid()
        if(((snHead.posX >= food.posX && snHead.posX < (food.posX+food.width)) || (snHead.posX <= food.posX && snHead.posX > (food.posX-food.width))) &&
            ((snHead.posY >= food.posY && snHead.posY < (food.posY+food.width)) || (snHead.posY <= food.posY && snHead.posY > (food.posY-food.width)))){
            food.enableToDraw = false
            CoroutineScope(Dispatchers.Default).launch {
                //delay(5000)
                food.foodMove(chart)
                food.enableToDraw = true
            }
            bodyGrow = 10
        }
        if(!isEating && bodyGrow == 0) {
            chart.chart[snTail[0].posX][snTail[0].posY] = 0
            when (snTail[0].dir) {
                "r" -> {
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snTail[0].posX - n][snTail[0].posY] = 0
                    }
                    snTail[0].posX += snMove.toInt()
                }
                "l" -> {
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snTail[0].posX + n][snTail[0].posY] = 0
                    }
                    snTail[0].posX -= snMove.toInt()
                }
                "d" -> {
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snTail[0].posX][snTail[0].posY - n] = 0
                    }
                    snTail[0].posY += snMove.toInt()
                }
                "u" -> {
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snTail[0].posX][snTail[0].posY + n] = 0
                    }
                    snTail[0].posY -= snMove.toInt()
                }
            }
            if (snTail.size > 1) {
                if (snTail[0].posX == snTail[1].posX && snTail[0].posY == snTail[1].posY) {
                    snTail.removeAt(0)
                    snTail.removeAt(0)
                }
            }
        }

        if(bodyGrow > 0)--bodyGrow
        return false
    }

//=====================================================================================================

    fun snackDraw(canvas: Canvas, paint: Paint, chart: Chart){
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        //paint.strokeWidth = 2.0f
        val path = Path()
        path.moveTo(snTail[0].posUnder.x.toFloat(), snTail[0].posUnder.y.toFloat())
        if(snTail.size > 1){
            for(i in 1..snTail.lastIndex){
                val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY+10).toFloat())
                if(snTail[i].dir != snTail[i-1].dir){
                    if(snTail[i].dir == "d" && snTail[i-1].dir == "r"){
                        path.arcTo(rectBend, 270f, 90f, false )
                    }else if(snTail[i].dir == "u" && snTail[i-1].dir == "l"){
                        path.arcTo(rectBend, 90f, 90f, false )
                    }else if(snTail[i].dir == "r" && snTail[i-1].dir == "u"){
                        path.arcTo(rectBend, 180f, 90f, false )
                    }else if(snTail[i].dir == "l" && snTail[i-1].dir == "d"){
                        path.arcTo(rectBend, 0f, 90f, false )
                    }else{
                        path.lineTo(snTail[i].posUnder.x.toFloat(), snTail[i].posUnder.y.toFloat())
                    }
                }else{
                    path.lineTo(snTail[i].posUnder.x.toFloat(), snTail[i].posUnder.y.toFloat())
                }
            }
        }
        path.lineTo(snHead.posUnder.x.toFloat(), snHead.posUnder.y.toFloat())
        path.lineTo(snHead.posBelow.x.toFloat(), snHead.posBelow.y.toFloat())
        path.lineTo(snTail[snTail.lastIndex].posBelow.x.toFloat(), snTail[snTail.lastIndex].posBelow.y.toFloat())
        if(snTail.size > 1){
            for(i in snTail.lastIndex-1 downTo 1){
                val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY+10).toFloat())
                if(snTail[i].dir != snTail[i+1].dir){
                    if(snTail[i].dir == "u" && snTail[i+1].dir == "l"){
                        path.arcTo(rectBend, 270f, 90f, false )
                    }else if(snTail[i].dir == "d" && snTail[i+1].dir == "r"){
                        path.arcTo(rectBend, 90f, 90f, false )
                    }else if(snTail[i].dir == "l" && snTail[i+1].dir == "d"){
                        path.arcTo(rectBend, 180f, 90f, false )
                    }else if(snTail[i].dir == "r" && snTail[i+1].dir == "u"){
                        path.arcTo(rectBend, 0f, 90f, false )
                    }else{
                        path.lineTo(snTail[i].posBelow.x.toFloat(), snTail[i].posBelow.y.toFloat())
                    }
                }else{
                    path.lineTo(snTail[i].posBelow.x.toFloat(), snTail[i].posBelow.y.toFloat())
                }
            }
        }
        path.lineTo(snTail[0].posBelow.x.toFloat(), snTail[0].posBelow.y.toFloat())
        path.close()
        canvas.drawPath(path, paint)
        val rectHead = RectF((snHead.posX-snHead.width).toFloat(), (snHead.posY-snHead.width).toFloat(), (snHead.posX+snHead.width).toFloat(), (snHead.posY+snHead.width).toFloat())
        canvas.drawArc(rectHead, snHead.headArc(), 180f, false, paint)
        val rectTail = RectF((snTail[0].posX-snHead.width).toFloat(), (snTail[0].posY-snHead.width).toFloat(), (snTail[0].posX+snHead.width).toFloat(), (snTail[0].posY+snHead.width).toFloat())
        canvas.drawArc(rectTail, snTail[0].tailArc(), 180f, false, paint)
        eyes.drawEyes(canvas, paint, snHead.dir)
        /*paint.color = Color.RED
        for(n in 0 until chart.chart.size){
            for(r in 0 until chart.chart[0].size){
                if(chart.chart[n][r] == 1){
                    canvas.drawPoint(n.toFloat(), r.toFloat(), paint)
                }
            }
        }*/
    }
}