package com.example.snack

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


class Snack(val food:Food) {

    var dir:String = "r"
    var turn:Boolean = false
    val turningAngle = 45
    var turningEnd = 0
    val snHead = Head(inPos = Point(100, 100))
    val snTail:MutableList<Tail> = mutableListOf(Tail(inPos = Point(20, 100), Point(20, 90), Point(20, 110), 10, "r"))
    val eyes = Eyes(food, snHead.posUnder, snHead.posBelow)
    val tongue = Tongue()
    val mouth = Mouth()
    val snMove:Float = 2.0f
    var isEating:Boolean = false
    var bodyGrow:Int = 0
    val enableTongueEdgeMax = 105
    val enableTongueEdgeMin = 100
    var enableTongueCounter = 0
    var enableMouth = false
    var mouthWidth = 0
    var drawHead = false
    var headTurn = 45


//=====================================================================================================

    fun snackMove(chart: Chart):Boolean{
        //if(chart.chart[if(snHead.posX < chart.chart.size)snHead.posX else chart.chart.size-1][if(snHead.posY < chart.chart[0].size)snHead.posY else chart.chart[0].size-1] == 1)return true
        chart.chart[snHead.posX][snHead.posY] = 1

        when(dir){
            "r" ->{
                if(turn){
                    if(headTurn == 45){
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                        snHead.posX = (if(snHead.dir == "d")snHead.posUnder.x else snHead.posBelow.x)+(10.0 * sin(Math.toRadians(if(snHead.dir == "d")225.0 else 315.0))).toInt()
                        snHead.posY = (if(snHead.dir == "d")snHead.posUnder.y else snHead.posBelow.y)-(10.0 * cos(Math.toRadians(if(snHead.dir == "d")225.0 else 315.0))).toInt()
                        headTurn = 90
                        drawHead = true
                    }else if(headTurn == 90){
                        snHead.posX = snTail[snTail.lastIndex].posX+10
                        snHead.posY = if(snTail[snTail.lastIndex].dir == "d")snTail[snTail.lastIndex].posY+10 else snTail[snTail.lastIndex].posY-10
                        snHead.dir = "r"
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                        turn = false
                        headTurn = 45
                    }
                }
                if(!turn){
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snHead.posX - n][snHead.posY] = 1
                    }
                    snHead.posX += snMove.toInt()
                }
            }
            //================================================================================
            "l" ->{
                if(turn){
                    if(headTurn == 45) {
                        snTail.add(
                            Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y), Point(snHead.posBelow.x, snHead.posBelow.y), 10, snHead.dir))
                        snHead.posX = (if(snHead.dir == "d")snHead.posBelow.x else snHead.posUnder.x)+(10.0 * sin(Math.toRadians(if(snHead.dir == "d")135.0 else 45.0))).toInt()
                        snHead.posY = (if(snHead.dir == "d")snHead.posBelow.y else snHead.posUnder.y)-(10.0 * cos(Math.toRadians(if(snHead.dir == "d")135.0 else 45.0))).toInt()
                        drawHead = true
                        headTurn = 90
                    }else if(headTurn == 90) {
                        snHead.posX = snTail[snTail.lastIndex].posX - 10
                        snHead.posY = if(snTail[snTail.lastIndex].dir == "d")snTail[snTail.lastIndex].posY + 10 else snTail[snTail.lastIndex].posY - 10
                        snHead.dir = "l"
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y), Point(snHead.posBelow.x, snHead.posBelow.y), 10, snHead.dir))
                        turn = false
                        headTurn = 45
                    }
                }
                if(!turn){
                    for (n in 1..snMove.toInt()) {
                        chart.chart[snHead.posX + n][snHead.posY] = 1
                    }
                    snHead.posX -= snMove.toInt()
                }
            }
            //==================================================================================
            "d" ->{
                if(turn){
                    if(headTurn == 45){
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                        //snHead.dir = "d"
                        snHead.posX = (if(snHead.dir == "r")snHead.posBelow.x else snHead.posUnder.x)+(10.0 * sin(Math.toRadians(if(snHead.dir == "r")45.0 else 315.0))).toInt()
                        snHead.posY = (if(snHead.dir == "r")snHead.posBelow.y else snHead.posUnder.y)-(10.0 * cos(Math.toRadians(if(snHead.dir == "r")45.0 else 315.0))).toInt()
                        drawHead = true
                        headTurn = 90
                    }else if(headTurn == 90){
                        snHead.posY = snTail[snTail.lastIndex].posY+10
                        snHead.posX = if(snTail[snTail.lastIndex].dir=="r")snTail[snTail.lastIndex].posX+10 else snTail[snTail.lastIndex].posX-10
                        snHead.dir = "d"
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y),Point(snHead.posBelow.x, snHead.posBelow.y),10, snHead.dir))
                        turn = false
                        headTurn = 45
                    }
                }
                if(!turn){
                    for(n in 1.. snMove.toInt()){
                        chart.chart[snHead.posX][snHead.posY - n] = 1
                    }
                    snHead.posY += snMove.toInt()
                }
            }
            //======================================================================================
            "u" ->{
                if(turn){
                    if(headTurn == 45) {
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y), Point(snHead.posBelow.x, snHead.posBelow.y), 10, snHead.dir))
                        snHead.posX = (if(snHead.dir == "r")snHead.posUnder.x else snHead.posBelow.x)+(10.0 * sin(Math.toRadians(if(snHead.dir == "r")135.0 else 225.0))).toInt()
                        snHead.posY = (if(snHead.dir == "r")snHead.posUnder.y else snHead.posBelow.y)-(10.0 * cos(Math.toRadians(if(snHead.dir == "r")135.0 else 225.0))).toInt()
                        drawHead = true
                        headTurn = 90
                    }else if(headTurn == 90) {
                        snHead.posY = snTail[snTail.lastIndex].posY-10
                        snHead.posX = if(snTail[snTail.lastIndex].dir == "r")snTail[snTail.lastIndex].posX+10 else snTail[snTail.lastIndex].posX-10
                        snHead.dir = "u"
                        snTail.add(Tail(Point(snHead.posX, snHead.posY), Point(snHead.posUnder.x, snHead.posUnder.y), Point(snHead.posBelow.x, snHead.posBelow.y), 10, snHead.dir))
                        turn = false
                        headTurn = 45
                    }
                }
                if(!turn){
                    for (n in 1..snMove.toInt()) {
                        chart.chart[snHead.posX][snHead.posY + n] = 1
                    }
                    snHead.posY -= snMove.toInt()
                }
            }
        }
        //=========== Move Tongue =================================================================

        if(((snHead.posX > (food.posX-food.width-enableTongueEdgeMax) && snHead.posX < (food.posX-food.width-enableTongueEdgeMin)) && (snHead.posY > (food.posY-food.width-enableTongueEdgeMin) && snHead.posY < (food.posY+food.width+enableTongueEdgeMin)))||
            ((snHead.posX < (food.posX+food.width+enableTongueEdgeMax) && snHead.posX > (food.posX+food.width+enableTongueEdgeMin)) && (snHead.posY > (food.posY-food.width-enableTongueEdgeMin) && snHead.posY < (food.posY+food.width+enableTongueEdgeMin)))||
            ((snHead.posY > (food.posY-food.width-enableTongueEdgeMax) && snHead.posY < (food.posY-food.width-enableTongueEdgeMin)) && (snHead.posX > (food.posX-food.width-enableTongueEdgeMin) && snHead.posX < (food.posX+food.width+enableTongueEdgeMin)))||
            ((snHead.posY < (food.posY+food.width+enableTongueEdgeMax) && snHead.posY > (food.posY+food.width+enableTongueEdgeMin)) && (snHead.posX > (food.posX-food.width-enableTongueEdgeMin) && snHead.posX < (food.posX+food.width+enableTongueEdgeMin)))
            ){
            if(enableTongueCounter == 0)enableTongueCounter = 7
        }
        if(enableTongueCounter != 0){
            tongue.moveTongue(pos = Point(snHead.posX, snHead.posY), dir=snHead.dir)
        }

        //============ Move Mouth =================================================================

        if((snHead.posX > (food.posX-food.width-50) && snHead.posX < (food.posX+food.width+50))&&
            (snHead.posY > (food.posY-food.width-50) && snHead.posY < (food.posY+food.width+50))
        ) enableMouth = true else enableMouth = false

        if(enableMouth && mouthWidth <20) ++mouthWidth else if(!enableMouth && mouthWidth > 0) --mouthWidth

        if(enableMouth || mouthWidth > 0){
            mouth.moveMouth(Point(snHead.posX, snHead.posY),mouthWidth, snHead.dir)
        }

        //============ Move Eyes ==================================================================
        eyes.movePupLid(pos1 = snHead.posUnder, pos2 = snHead.posBelow, dir = snHead.dir)

        //============ Move Food and Body Grow ====================================================
        if(((snHead.posX >= food.posX && snHead.posX < (food.posX+food.width)) ||
                    (snHead.posX <= food.posX && snHead.posX > (food.posX-food.width))) &&
            ((snHead.posY >= food.posY && snHead.posY < (food.posY+food.width)) ||
                    (snHead.posY <= food.posY && snHead.posY > (food.posY-food.width)))
            ){
            food.enableToDraw = false

            bodyGrow = 10
        }

        //=========== Move Tail ===================================================================
        if(!isEating && bodyGrow == 0 && !turn) {
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
        if(enableTongueCounter > 0)--enableTongueCounter

        return false
    }

//=====================================================================================================

    fun snackDraw(canvas: Canvas, paint: Paint, chart: Chart){

        if(enableTongueCounter != 0) tongue.drawTongue(canvas, paint)
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        //paint.strokeWidth = 2.0f
        val path = Path()
        path.moveTo(snTail[0].posUnder.x.toFloat(), snTail[0].posUnder.y.toFloat())
        if(snTail.size > 1){
            for(i in 1..snTail.lastIndex){
                //val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-20).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY).toFloat())
                if(snTail[i].dir != snTail[i-1].dir){
                    if(snTail[i].dir == "d" && snTail[i-1].dir == "r"){
                        val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-20).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY).toFloat())
                        path.arcTo(rectBend, 270f, 90f, false )
                    }else if(snTail[i].dir == "u" && snTail[i-1].dir == "l"){
                        val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY+20).toFloat())
                        path.arcTo(rectBend, 90f, 90f, false )
                    }else if(snTail[i].dir == "r" && snTail[i-1].dir == "u"){
                        val rectBend = RectF((snTail[i].posX-20).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX).toFloat(),(snTail[i].posY+10).toFloat())
                        path.arcTo(rectBend, 180f, 90f, false )
                    }else if(snTail[i].dir == "l" && snTail[i-1].dir == "d"){
                        val rectBend = RectF((snTail[i].posX).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX+20).toFloat(),(snTail[i].posY+10).toFloat())
                        path.arcTo(rectBend, 0f, 90f, false )
                    }else{
                        path.lineTo(snTail[i].posUnder.x.toFloat(), snTail[i].posUnder.y.toFloat())
                    }
                }else{
                    path.lineTo(snTail[i].posUnder.x.toFloat(), snTail[i].posUnder.y.toFloat())
                }
            }
        }
        if(drawHead){
            path.lineTo(snTail[snTail.lastIndex].posUnder.x.toFloat(), snTail[snTail.lastIndex].posUnder.y.toFloat())
            //path.lineTo((snHead.posUnder.x+(10*sin(Math.toRadians(45.0)))).toFloat(), (snHead.posUnder.y-(10*cos(Math.toRadians(45.0)))).toFloat())

            if(snTail[snTail.lastIndex].dir == "r" && dir == "d"){//i
                val rectBend = RectF((snTail[snTail.lastIndex].posX).toFloat(), (snTail[snTail.lastIndex].posY-10).toFloat(), (snTail[snTail.lastIndex].posX+20).toFloat(),(snTail[snTail.lastIndex].posY+40).toFloat())
                path.arcTo(rectBend, 270f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "l" && dir == "u"){//i
                val rectBend = RectF((snTail[snTail.lastIndex].posX-20).toFloat(), (snTail[snTail.lastIndex].posY-40).toFloat(), (snTail[snTail.lastIndex].posX).toFloat(),(snTail[snTail.lastIndex].posY+10).toFloat())
                path.arcTo(rectBend, 90f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "r" && dir == "u"){//i
                val rectBend = RectF((snTail[snTail.lastIndex].posX).toFloat(), (snTail[snTail.lastIndex].posY-40).toFloat(), (snTail[snTail.lastIndex].posX+20).toFloat(),(snTail[snTail.lastIndex].posY+10).toFloat())
                path.arcTo(rectBend, 45f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "l" && dir == "d"){//i
                val rectBend = RectF((snTail[snTail.lastIndex].posX-20).toFloat(), (snTail[snTail.lastIndex].posY-10).toFloat(), (snTail[snTail.lastIndex].posX).toFloat(),(snTail[snTail.lastIndex].posY+40).toFloat())
                path.arcTo(rectBend, 225f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "u" && dir == "l"){//i
                val rectBend = RectF((snTail[snTail.lastIndex].posX-40).toFloat(), (snTail[snTail.lastIndex].posY-20).toFloat(), (snTail[snTail.lastIndex].posX+10).toFloat(),(snTail[snTail.lastIndex].posY).toFloat())
                path.arcTo(rectBend, 315f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "d" && dir == "r"){
                val rectBend = RectF((snTail[snTail.lastIndex].posX-10).toFloat(), (snTail[snTail.lastIndex].posY).toFloat(), (snTail[snTail.lastIndex].posX+40).toFloat(),(snTail[snTail.lastIndex].posY+20).toFloat())
                path.arcTo(rectBend, 135f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "d" && dir == "l"){
                val rectBend = RectF((snTail[snTail.lastIndex].posX-40).toFloat(), (snTail[snTail.lastIndex].posY).toFloat(), (snTail[snTail.lastIndex].posX+10).toFloat(),(snTail[snTail.lastIndex].posY+20).toFloat())
                path.arcTo(rectBend, 0f, 45f, false )
            }else if(snTail[snTail.lastIndex].dir == "u" && dir == "r"){
                val rectBend = RectF((snTail[snTail.lastIndex].posX-10).toFloat(), (snTail[snTail.lastIndex].posY-20).toFloat(), (snTail[snTail.lastIndex].posX+40).toFloat(),(snTail[snTail.lastIndex].posY).toFloat())
                path.arcTo(rectBend, 180f, 45f, false )
            }
            path.lineTo(snTail[snTail.lastIndex].posBelow.x.toFloat(), snTail[snTail.lastIndex].posBelow.y.toFloat())
        }else{
            path.lineTo(snHead.posUnder.x.toFloat(), snHead.posUnder.y.toFloat())
            path.lineTo(snHead.posBelow.x.toFloat(), snHead.posBelow.y.toFloat())
            path.lineTo(snTail[snTail.lastIndex].posBelow.x.toFloat(), snTail[snTail.lastIndex].posBelow.y.toFloat())
        }

        if(snTail.size > 1){
            for(i in snTail.lastIndex-1 downTo 1){
                //val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-20).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY).toFloat())
                if(snTail[i].dir != snTail[i+1].dir){
                    if(snTail[i].dir == "u" && snTail[i+1].dir == "l"){
                        val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY-20).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY).toFloat())
                        path.arcTo(rectBend, 270f, 90f, false )
                    }else if(snTail[i].dir == "d" && snTail[i+1].dir == "r"){
                        val rectBend = RectF((snTail[i].posX-10).toFloat(), (snTail[i].posY).toFloat(), (snTail[i].posX+10).toFloat(),(snTail[i].posY+20).toFloat())
                        path.arcTo(rectBend, 90f, 90f, false )
                    }else if(snTail[i].dir == "l" && snTail[i+1].dir == "d"){
                        val rectBend = RectF((snTail[i].posX-20).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX).toFloat(),(snTail[i].posY+10).toFloat())
                        path.arcTo(rectBend, 180f, 90f, false )
                    }else if(snTail[i].dir == "r" && snTail[i+1].dir == "u"){
                        val rectBend = RectF((snTail[i].posX).toFloat(), (snTail[i].posY-10).toFloat(), (snTail[i].posX+20).toFloat(),(snTail[i].posY+10).toFloat())
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
            //val rec = RectF((350-snHead.width).toFloat(), (350-snHead.width).toFloat(), (350+snHead.width).toFloat(), (350+snHead.width).toFloat())
            //canvas.drawArc(rec, 0f+45f, 180f, false, paint)
        if(drawHead){
            drawHead = false
            //paint.color= Color.GREEN
            //val rectHead = RectF((snHead.posX-snHead.width).toFloat(), (snHead.posY-snHead.width).toFloat(), (snHead.posX+snHead.width).toFloat(), (snHead.posY+snHead.width).toFloat())
            //canvas.drawArc(rectHead, snHead.headArc(), 180f, false, paint)
            val rectHead = RectF((snHead.posX-snHead.width).toFloat(), (snHead.posY-snHead.width).toFloat(), (snHead.posX+snHead.width).toFloat(), (snHead.posY+snHead.width).toFloat())
            if(snTail[snTail.lastIndex].dir == "r" && dir == "d"){
                canvas.drawArc(rectHead, 270f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "l" && dir == "u"){
                canvas.drawArc(rectHead, 90f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "r" && dir == "u"){
                canvas.drawArc(rectHead, 180f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "l" && dir == "d"){
                canvas.drawArc(rectHead, 0f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "u" && dir == "l"){
                canvas.drawArc(rectHead, 90f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "d" && dir == "r"){
                canvas.drawArc(rectHead, 270f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "d" && dir == "l"){
                canvas.drawArc(rectHead, 0f+45f, 180f, false, paint)
            }else if(snTail[snTail.lastIndex].dir == "u" && dir == "r"){
                canvas.drawArc(rectHead, 180f+45f, 180f, false, paint)
            }
        }else{
            val rectHead = RectF((snHead.posX-snHead.width).toFloat(), (snHead.posY-snHead.width).toFloat(), (snHead.posX+snHead.width).toFloat(), (snHead.posY+snHead.width).toFloat())
            canvas.drawArc(rectHead, snHead.headArc(), 180f, false, paint)
        }

        val rectTail = RectF((snTail[0].posX-snHead.width).toFloat(), (snTail[0].posY-snHead.width).toFloat(), (snTail[0].posX+snHead.width).toFloat(), (snTail[0].posY+snHead.width).toFloat())
        canvas.drawArc(rectTail, snTail[0].tailArc(), 180f, false, paint)

        //eyes.drawEyes(canvas, paint, snHead.dir)

        if(enableMouth || mouthWidth > 0) mouth.drawMouth(canvas, paint)
        //turn = false




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