package com.cpe.funconnect.funconnect.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.cpe.funconnect.funconnect.*
import com.cpe.funconnect.funconnect.controlers.UserRequestControler
import com.cpe.funconnect.funconnect.controlers.UserRequestControlers
import com.cpe.funconnect.funconnect.model.PaintView
import com.cpe.funconnect.funconnect.services.MyFirebaseMessagingService
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_draw.*


abstract class DrawActivity : AppCompatActivity(), ConnectionInterface {


    protected var paintView: PaintView? = null
    protected var progressBar: ProgressBar? = null
    protected var userControl: UserRequestControlers? = null
    protected var gson: Gson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initValues()
    }

    /**
     * Inflate refresh button
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handles onClick event on the refresh button
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear -> {
                paintView?.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Handles reply from asyncTask connexion
     * To be further implemented inside the derived classes
     */
    override fun onPostReply(success: Boolean) {
        paintView?.clear()
        progressBar?.visibility = View.GONE
        paintView?.visibility = View.VISIBLE
    }

    /**
     * Launches the asynchronous flow
     */
    abstract fun sendTasks()

    /**
     * Initiate the complete view on the screen
     */
    private fun initValues() {
        initView()
        initGson()
        initUser()

        sendSig.setOnClickListener {
            sendTasks()
        }
    }

    /**
     * Prepares the view elements
     */
    private fun initView() {
        paintView = findViewById(R.id.paintView)
        progressBar = findViewById(R.id.progressBar)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView?.init(metrics)
    }

    /**
     * Prepares the user depending on the entry point in the app
     */
    private fun initUser() {
        if (this.getSharedPreferences("_", Context.MODE_PRIVATE).getString(
                EnvironmentVariables.MAIL,
                "empty") != "empty") {
            userControl = UserRequestControler(
                this.getSharedPreferences("_", Context.MODE_PRIVATE).getString(EnvironmentVariables.MAIL, "empty"),
                MyFirebaseMessagingService.getToken(this)
            )
        } else {
            userControl = UserRequestControler(
                intent.getStringExtra(EnvironmentVariables.EMAIL),
                MyFirebaseMessagingService.getToken(this)
            )
        }
    }

    /**
     * Prepares the Gson module
     */
    private fun initGson() {
        gson = Gson()
        val gsonBuilder = GsonBuilder()
        gson = gsonBuilder.create()
    }
}
