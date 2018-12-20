package com.cpe.funconnect.funconnect

import android.graphics.Path

class FingerPath {

    var color: Int = 0
    var emboss: Boolean = false
    var blur: Boolean = false
    var strokeWidth: Float = 0.toFloat()
    lateinit var path: Path

    constructor(color: Int, emboss: Boolean, blur: Boolean, strokeWidth: Float, path: Path){
        this.color = color
        this.emboss = emboss
        this.blur = blur
        this.strokeWidth = strokeWidth
        this.path = path
    }
}