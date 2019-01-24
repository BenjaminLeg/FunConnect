package com.cpe.funconnect.funconnect.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import kotlinx.android.synthetic.main.activity_idle.*

class IdleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idle)
        val mail = this.getSharedPreferences("_", Context.MODE_PRIVATE).getString(EnvironmentVariables.MAIL, "empty")
        profileMail.text = "Your Mail : " + mail
    }
}
