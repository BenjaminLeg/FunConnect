package com.cpe.funconnect.funconnect

import android.os.Bundle
import android.view.View
import com.cpe.funconnect.funconnect.Tools.Signature
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject
import java.io.File

class DrawRegister : DrawActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Registering : $attempt")
    }

    override fun onLastReply(text : Boolean) {
        super.onLastReply(text)
        checkAttempts(attempt)
    }


    override fun sendTasks(){
        super.sendTasks()
        attempt++
        signatures.add(paintView!!.getCoord())
        attemptText.setText("Registering : $attempt")
        paintView?.clear()
        if(attempt >= 6){
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

    fun checkAttempts(attempts : Int){
        if(attempts >= 6){
            finish()
        }
    }

        fun readToken(): List<String>{
           return  File("token.txt").readLines()
        }

}