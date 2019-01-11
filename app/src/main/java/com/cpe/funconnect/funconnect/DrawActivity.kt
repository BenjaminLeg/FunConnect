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


abstract class DrawActivity : AppCompatActivity(), ConnectionInterface {


    private var paintView: PaintView? = null
    private var communicationTask: CommunicationTask? = null
    private var progress: ProgressBar? = null
    protected var attempt: Int = 0
    private val MAX_ATTEMPT: Int = 3
    private val FIRST_CONNECT_ATTEMPT: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintView = findViewById(R.id.paintView) as PaintView
        progress = findViewById(R.id.progressBar) as ProgressBar
        attempt = 1
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var paint = paintView
        paint?.init(metrics)


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
        attempt++
        progress?.visibility = View.GONE
        paintView?.clear()
        paintView?.visibility = View.VISIBLE
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun sendTasks(){
        paintView?.visibility = View.INVISIBLE
        progress?.visibility = View.VISIBLE
        var jsonMaker = JSONmaker()
        jsonMaker.createJSON(paintView?.getCoord())
        communicationTask = CommunicationTask(jsonMaker.jsonObject, this)
        communicationTask?.execute()
    }
}
