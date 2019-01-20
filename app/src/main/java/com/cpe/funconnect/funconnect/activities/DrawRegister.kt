package com.cpe.funconnect.funconnect.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cpe.funconnect.funconnect.task.RegisterTask
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawRegister : DrawActivity() {

    protected var registerTask: RegisterTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text = "Registering : ${user?.getAttempt()}/${EnvironmentVariables.ATTEMPT_REGISTER}"
    }

    override fun onLastReply(success : Boolean) {
        super.onLastReply(success)
        if (success){
            getSharedPreferences("_", FirebaseMessagingService.MODE_PRIVATE).edit().putString("mail", user?.getEmail()).apply()
            Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show()
            val intent = Intent(this, IdleActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this, RegisterTask.answer, Toast.LENGTH_LONG).show()
        }
    }


    override fun sendTasks(){
        if(user!!.getAttempt() != EnvironmentVariables.ATTEMPT_REGISTER){user!!.addAttempt()}
        else{
            buildRequest()
        }
        user?.addSignature(paintView!!.getCoord())
        attemptText.text = "Registering : ${user?.getAttempt()}/${EnvironmentVariables.ATTEMPT_REGISTER}"
        paintView?.clear()
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