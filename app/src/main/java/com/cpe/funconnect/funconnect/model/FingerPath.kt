package com.cpe.funconnect.funconnect.model

import android.graphics.Path

class FingerPath {

    var color: Int = 0

    var strokeWidth: Float = 0.toFloat()
    lateinit var path: Path

    constructor(color: Int, strokeWidth: Float, path: Path){
        this.color = color
        this.strokeWidth = strokeWidth
        this.path = path
    }
}