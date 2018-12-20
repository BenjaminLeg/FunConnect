package com.cpe.funconnect.funconnect

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_entry.*

class Entry_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        init()
    }

    fun init(){
        buttonSignUp.setOnClickListener{
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        buttonConnect.setOnClickListener {
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
        }
    }
}
