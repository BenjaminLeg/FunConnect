package com.cpe.funconnect.funconnect

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawConnect: DrawActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Attempt : $attempt")
    }

    override fun onLastReply(text : Boolean) {
        super.onLastReply(text)
        attempt++
        checkAttempts(attempt)
        attemptText.setText("Attempt : $attempt")
    }

    fun checkAttempts(attempts : Int){
        if(attempts >= 4){
            finish()
        }
    }


    override fun sendTasks(){
        super.sendTasks()
        paintView?.visibility = View.INVISIBLE
        progress?.visibility = View.VISIBLE
        signatures.add(paintView!!.getCoord())
        user =User(signatures, this.getSharedPreferences("_", Context.MODE_PRIVATE).getString("mail", "empty"), MyFirebaseMessagingService.getToken(this))
        var newUser = gson.toJson(user)
        var jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        communicationTask = CommunicationTask(jsonSend, this)
        communicationTask?.execute()
    }
}