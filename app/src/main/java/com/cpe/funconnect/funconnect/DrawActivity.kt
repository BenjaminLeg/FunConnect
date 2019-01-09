package com.cpe.funconnect.funconnect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_draw.*


class DrawActivity : AppCompatActivity(), ConnectionInterface {


    private var paintView: PaintView? = null
    private var communicationTask: CommunicationTask? = null
    private var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
        paintView = findViewById(R.id.paintView) as PaintView
        progress = findViewById(R.id.progressBar) as ProgressBar
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var paint = paintView
        if(paint is PaintView) {
            paint.init(metrics)
        }

        sendSig.setOnClickListener {
            sendTasks()
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

    override fun onLastReply(text : String?) {
        progress?.visibility = View.GONE
        paintView?.clear()
        paintView?.visibility = View.VISIBLE
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun sendTasks(){
        paintView?.visibility = View.INVISIBLE
        progress?.visibility = View.VISIBLE
        var jsonMaker = JSONmaker()
        jsonMaker.createJSON(paintView?.coords)
        communicationTask = CommunicationTask(jsonMaker.jsonObject, this)
        communicationTask?.execute()
        //finish()
    }
}
