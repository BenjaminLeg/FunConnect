package com.cpe.funconnect.funconnect

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, "KO", Toast.LENGTH_LONG).show()
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
        user =User(signatures, getIntent().getStringExtra("email"), MyFirebaseMessagingService.getToken(this))
        var newUser = gson.toJson(user)
        var jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        communicationTask = CommunicationTask(jsonSend, this)
        communicationTask?.execute()
    }

}