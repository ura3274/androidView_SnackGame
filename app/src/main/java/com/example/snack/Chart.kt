package com.example.snack

import android.content.res.Resources

class Chart {
    val displayMetrics = Resources.getSystem().displayMetrics
    val chart: Array<Array<Int>> = Array(displayMetrics.widthPixels, { Array(displayMetrics.heightPixels, {0}) })

    init{
        for(i in 0 .. (displayMetrics.widthPixels-1)) {
            for (j in 0 .. (displayMetrics.heightPixels-1)) {

                if (i == 0 || i == (displayMetrics.widthPixels-1)) {
                    chart[i][j] = 1
                } else if (j == 0 || j == (displayMetrics.heightPixels - 1)) {
                    chart[i][j] = 1
                }
            }
        }
    }
}