package com.cpe.funconnect.funconnect.activities

import android.app.AlertDialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.cpe.funconnect.funconnect.*
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.cpe.funconnect.funconnect.task.ConnectTask
import com.cpe.funconnect.funconnect.task.RegisterTask.Companion.answer
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.SPLASH_DELAY
import kotlinx.android.synthetic.main.activity_draw.*
import kotlinx.android.synthetic.main.validation_layout.*
import org.json.JSONObject
import android.os.Build
import com.cpe.funconnect.funconnect.utils.Utils.Companion.handleError


class DrawConnect: DrawActivity() {

    private var connectTask: ConnectTask? = null
    private var mDelayHandler: Handler? = null
    private lateinit var rocketAnimation: AnimatedVectorDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text = getString(R.string.Attempt, userControl!!.getAttempt(), EnvironmentVariables.MAX_ATTEMPT_CONNECT)
        expandcollapse.apply {
            rocketAnimation = background as AnimatedVectorDrawable
        }
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            finish()
        }
    }

    /**
     * Handles answers from the connection request
     */
    override fun onPostReply(success : Boolean) {
        super.onPostReply(success)
        if (success){
            onSuccess()
        }
        else{
            onFailure()
        }

    }

    override fun sendTasks(){
        showProgress(true)
        userControl?.addSignature(paintView!!.getCoord())
        connectTask = ConnectTask(JSONObject(gson!!.toJson(userControl!!.getSignature(0))), this)
        connectTask?.execute()
    }

    /**
     * Handles correct reply, shows validation screen
     */
    private fun onSuccess(){
        //Initialize the Handler
        mDelayHandler = Handler()
        showProgress(false)
        rocketAnimation.start()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    /**
     * Handles wrong reply, shows alert dialog
     */
    private fun onFailure(){
        if(userControl!!.getAttempt() != EnvironmentVariables.MAX_ATTEMPT_CONNECT){
            if(answer != "invalid entry"){
                handleError(this, answer)
            }else{
                userControl!!.addAttempt()
                showAlert()
            }
        }
        else{
            finish()
        }
        attemptText.text = getString(R.string.Attempt, userControl!!.getAttempt(), EnvironmentVariables.MAX_ATTEMPT_CONNECT)
    }

    /**
     * Selects what is displayed
     */
    private fun showProgress(resp : Boolean){
        if(resp){
            paintView?.visibility = View.INVISIBLE
            progressBar?.visibility = View.VISIBLE
        }
        else{
            drawLayout.visibility = INVISIBLE
            progressBar?.visibility = INVISIBLE
            validation.visibility = VISIBLE
        }
    }

    /**
     * Creates an alert in case of wrong entry
     */
    private fun showAlert(){
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(this)
        }
        builder.setTitle("Wrong Entry")
            .setMessage("Please try again")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    companion object {
        private const val TAG = "DrawConnect"
    }
}