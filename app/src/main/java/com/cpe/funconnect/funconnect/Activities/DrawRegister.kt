package com.cpe.funconnect.funconnect.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cpe.funconnect.funconnect.Task.CommunicationTask
import com.cpe.funconnect.funconnect.Services.MyFirebaseMessagingService
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.Model.User
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawRegister : DrawActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Registering : $attempt")
    }

    override fun onLastReply(success : Boolean) {
        super.onLastReply(success)
        if(success){
            Toast.makeText(this, "Correctly logged", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, "Network issue", Toast.LENGTH_LONG).show()
        }
    }


    override fun sendTasks(){
        if(attempt+1 != 6){attempt++}
        else{
            buildRequest()
            finish()
        }
        signatures.add(paintView!!.getCoord())
        attemptText.setText("Registering : $attempt")
        paintView?.clear()
    }

    fun buildRequest(){
        paintView?.visibility = View.INVISIBLE
        progress?.visibility = View.VISIBLE
        user = User(
            signatures,
            getIntent().getStringExtra("email"),
            MyFirebaseMessagingService.getToken(this)
        )
        var newUser = gson.toJson(user)
        var jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        communicationTask = CommunicationTask(jsonSend, this)
        communicationTask?.execute()
    }

}