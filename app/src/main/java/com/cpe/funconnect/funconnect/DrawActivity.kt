package com.cpe.funconnect.funconnect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem


class DrawActivity : AppCompatActivity() {

    private var paintView: PaintView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
        paintView = findViewById(R.id.paintView) as PaintView
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var paint = paintView
        if(paint is PaintView) {
            paint.init(metrics)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.clear -> {
                paintView?.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
