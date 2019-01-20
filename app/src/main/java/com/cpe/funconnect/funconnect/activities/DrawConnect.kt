package com.cpe.funconnect.funconnect.activities

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.cpe.funconnect.funconnect.*
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.cpe.funconnect.funconnect.task.ConnectTask
import com.cpe.funconnect.funconnect.task.RegisterTask.Companion.answer
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.SPLASH_DELAY
import kotlinx.android.synthetic.main.activity_draw.*
import kotlinx.android.synthetic.main.validation_layout.*
import org.json.JSONObject

class DrawConnect: DrawActivity() {

    private var connectTask: ConnectTask? = null
    private var mDelayHandler: Handler? = null
    private lateinit var rocketAnimation: AnimatedVectorDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text = getString(R.string.Attempt, user!!.getAttempt(), EnvironmentVariables.MAX_ATTEMPT_CONNECT)
        expandcollapse.apply {
            rocketAnimation = background as AnimatedVectorDrawable
        }
    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            finish()
        }
    }

    override fun onPostReply(success : Boolean) {
        super.onPostReply(success)
        if (success){

            //Initialize the Handler
            mDelayHandler = Handler()
            drawLayout.visibility = INVISIBLE
            progressBar?.visibility = INVISIBLE
            validation.visibility = VISIBLE

            rocketAnimation.start()

            //Navigate with delay
            mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
        }
        else{
            if(user!!.getAttempt() != EnvironmentVariables.MAX_ATTEMPT_CONNECT){
                if(answer != "No Internet connexion"){
                    user!!.addAttempt()
                }
            }
            else{finish()}
            attemptText.text = getString(R.string.Attempt, user!!.getAttempt(), EnvironmentVariables.MAX_ATTEMPT_CONNECT)
            Toast.makeText(this, answer, Toast.LENGTH_LONG).show()
        }

    }

    override fun sendTasks(){
        paintView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        user?.addSignature(paintView!!.getCoord())

        Log.d(TAG, "User : ${user.toString()}")

        val newUser = gson!!.toJson(user)
        val jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        connectTask = ConnectTask(jsonSend, this)
        connectTask?.execute()
    }

    companion object {
        private const val TAG = "DrawConnect"
    }
}