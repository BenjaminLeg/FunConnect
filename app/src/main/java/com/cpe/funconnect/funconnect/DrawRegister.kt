package com.cpe.funconnect.funconnect

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_draw.*

class DrawRegister : DrawActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Registering : $attempt")
    }

    override fun onLastReply(text : String?) {
        super.onLastReply(text)
        checkAttempts(attempt)
        attemptText.setText("Registering : $attempt")
    }

    fun checkAttempts(attempts : Int){
        if(attempts >= 6){
            finish()
        }
    }

}