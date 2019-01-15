package com.cpe.funconnect.funconnect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawConnect: DrawActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Attempt : $attempt")
    }

    override fun onLastReply(success : Boolean) {
        super.onLastReply(success)
        if (success){
            val intent = Intent(this, ValidationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(attempt+1 != 4){attempt++}
        else{finish()}
        attemptText.setText("Attempt : $attempt")
    }

    override fun sendTasks(){
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