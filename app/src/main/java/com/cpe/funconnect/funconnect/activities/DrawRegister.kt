package com.cpe.funconnect.funconnect.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cpe.funconnect.funconnect.task.RegisterTask
import com.cpe.funconnect.funconnect.services.MyFirebaseMessagingService
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.model.User
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawRegister : DrawActivity() {

    protected var registerTask: RegisterTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text = "Registering : $attempt/5"
    }

    override fun onLastReply(success : Boolean) {
        super.onLastReply(success)
        if (success){
            Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show()
            finish()
        }
        else{
            Toast.makeText(this, RegisterTask.answer, Toast.LENGTH_LONG).show()
        }
    }


    override fun sendTasks(){
        if(attempt+1 != 6){attempt++}
        else{
            buildRequest()
            finish()
        }
        signatures.add(paintView!!.getCoord())
        attemptText.text = "Registering : $attempt/5"
        paintView?.clear()
    }

    private fun buildRequest(){
        paintView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        user = User(
            signatures,
            this.intent.getStringExtra("email"),
            MyFirebaseMessagingService.getToken(this)
        )
        val newUser = gson.toJson(user)
        val jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        registerTask = RegisterTask(jsonSend, this)
        registerTask?.execute()
    }

}