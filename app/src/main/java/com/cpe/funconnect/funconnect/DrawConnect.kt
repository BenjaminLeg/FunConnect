package com.cpe.funconnect.funconnect

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_draw.*

class DrawConnect: DrawActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_draw)
        super.onCreate(savedInstanceState)
        attemptText.setText("Attempt : $attempt")
    }

    override fun onLastReply(text : String?) {
        super.onLastReply(text)
        checkAttempts(attempt)
        attemptText.setText("Attempt : $attempt")
    }

    fun checkAttempts(attempts : Int){
        if(attempts >= 4){
            finish()
        }
    }
}