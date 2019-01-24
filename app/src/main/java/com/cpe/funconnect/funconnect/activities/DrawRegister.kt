package com.cpe.funconnect.funconnect.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cpe.funconnect.funconnect.task.RegisterTask
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.task.RegisterTask.Companion.answer
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.cpe.funconnect.funconnect.utils.Utils
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawRegister : DrawActivity() {

    private var registerTask: RegisterTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text =
                getString(R.string.Register, userControl?.getAttempt(), EnvironmentVariables.ATTEMPT_REGISTER)
    }

    /**
     * Handles the answer from the register request
     */
    override fun onPostReply(success: Boolean) {
        super.onPostReply(success)
        if (success) {
            onSuccess()
        } else {
            Utils.handleError(this, answer)
        }
    }

    /**
     * Behavior on a successful answer
     */
    private fun onSuccess() {
        getSharedPreferences("_", FirebaseMessagingService.MODE_PRIVATE).edit()
            .putString(EnvironmentVariables.MAIL, userControl?.getEmail()).apply()
        Toast.makeText(this, getString(R.string.Registered), Toast.LENGTH_LONG).show()
        val intentIdle = Intent(this, IdleActivity::class.java)
        intentIdle.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intentIdle.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intentIdle)
        finish()
    }

    /**
     * Checks the number of attempts and prepare the request before sending it
     */
    override fun sendTasks() {
        userControl?.addSignature(paintView!!.getCoord())
        paintView?.clear()
        if (userControl!!.getAttempt() != EnvironmentVariables.ATTEMPT_REGISTER) {
            userControl!!.addAttempt()
            attemptText.text =
                    getString(R.string.Register, userControl?.getAttempt(), EnvironmentVariables.ATTEMPT_REGISTER)
        } else {
            buildRequest()
        }
    }

    /**
     * Builds up the JSON format for sending it to the server
     */
    private fun buildRequest() {
        showProgress()
        registerTask = RegisterTask(JSONObject(gson!!.toJson(userControl!!.getUser())), this)
        registerTask?.execute()
    }

    /**
     * Hides paintView and shows the progressBar
     */
    private fun showProgress() {
        paintView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
    }

}