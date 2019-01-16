package com.cpe.funconnect.funconnect.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cpe.funconnect.funconnect.*
import com.cpe.funconnect.funconnect.model.User
import com.cpe.funconnect.funconnect.services.MyFirebaseMessagingService
import com.cpe.funconnect.funconnect.task.ConnectTask
import com.cpe.funconnect.funconnect.task.RegisterTask
import com.cpe.funconnect.funconnect.task.RegisterTask.Companion.answer
import kotlinx.android.synthetic.main.activity_draw.*
import org.json.JSONObject

class DrawConnect: DrawActivity() {

    protected var connectTask: ConnectTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.text = "Attempt : $attempt"
    }

    override fun onLastReply(success : Boolean) {
        super.onLastReply(success)
        if (success){
            val intent = Intent(this, ValidationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            if(attempt+1 != 4 ){
                if(answer != "No Internet connexion"){
                    attempt++
                }
            }
            else{finish()}
            attemptText.text = "Attempt : $attempt"
            Toast.makeText(this, answer, Toast.LENGTH_LONG).show()
        }

    }

    override fun sendTasks(){
        paintView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        signatures.add(paintView!!.getCoord())
        user = User(
            signatures,
            this.getSharedPreferences("_", Context.MODE_PRIVATE).getString("mail", "empty"),
            MyFirebaseMessagingService.getToken(this)
        )

        Log.d(TAG, "User : ${user.toString()}")

        val newUser = gson.toJson(user)
        val jsonSend = JSONObject()
        jsonSend.put("User", JSONObject(newUser))
        connectTask = ConnectTask(jsonSend, this)
        connectTask?.execute()
    }

    companion object {
        private const val TAG = "DrawConnect"
    }
}