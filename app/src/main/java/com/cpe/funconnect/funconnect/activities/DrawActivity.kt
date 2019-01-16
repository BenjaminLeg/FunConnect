package com.cpe.funconnect.funconnect.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.cpe.funconnect.funconnect.*
import com.cpe.funconnect.funconnect.model.PaintView
import com.cpe.funconnect.funconnect.model.Signature
import com.cpe.funconnect.funconnect.model.User
import com.cpe.funconnect.funconnect.task.RegisterTask
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_draw.*


abstract class DrawActivity : AppCompatActivity(), ConnectionInterface {


    protected var paintView: PaintView? = null
    protected var progressBar: ProgressBar? = null
    protected var attempt: Int = 1
    protected var user : User? = null
    protected lateinit var gson : Gson
    protected lateinit var signatures : ArrayList<Signature>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintView = findViewById(R.id.paintView)
        progressBar = findViewById(R.id.progressBar)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView?.init(metrics)
        signatures = ArrayList()

        gson = Gson()
        val gsonBuilder = GsonBuilder()
        gson = gsonBuilder.create()


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
        when (item.itemId) {
            R.id.clear -> {
                paintView?.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onLastReply(success : Boolean) {
        paintView?.clear()
        progressBar?.visibility = View.GONE
        paintView?.visibility = View.VISIBLE
    }

    abstract fun sendTasks()
}
