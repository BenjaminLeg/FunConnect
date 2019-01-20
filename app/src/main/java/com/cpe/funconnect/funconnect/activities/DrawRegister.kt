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
        attemptText.text = getString(R.string.Register, user?.getAttempt(), EnvironmentVariables.ATTEMPT_REGISTER)
    }

    override fun onPostReply(success : Boolean) {
        super.onPostReply(success)
        if (success){
            onSuccess()
        }
        else{
            Utils.handleError(this, answer)
        }
    }


    private fun onSuccess(){
        getSharedPreferences("_", FirebaseMessagingService.MODE_PRIVATE).edit().putString("mail", user?.getEmail()).apply()
        Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show()
        val intent = Intent(this, IdleActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun sendTasks(){
        user?.addSignature(paintView!!.getCoord())
        paintView?.clear()
        if(user!!.getAttempt() != EnvironmentVariables.ATTEMPT_REGISTER){
            user!!.addAttempt()
            attemptText.text = getString(R.string.Register, user?.getAttempt(), EnvironmentVariables.ATTEMPT_REGISTER)
        }
        else{
            buildRequest()
        }
    }

    private fun buildRequest(){
        paintView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        val newUser = gson!!.toJson(user)
        val jsonSend = JSONObject()
        jsonSend.put("userRegistrationRequest", JSONObject(newUser))
        registerTask = RegisterTask(jsonSend, this)
        registerTask?.execute()
    }

}