package com.cpe.funconnect.funconnect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View

class DrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
        val layout1 = findViewById(R.id.layout1) as android.support.constraint.ConstraintLayout
        val canvass = Canvass(this)
        layout1.addView(canvass)
    }

    class Canvass(context: Context) : View(context) {

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(255, 255, 0)
            val width = getWidth()
            val paint = Paint()
            canvas.drawFilter
        }
    }
}
