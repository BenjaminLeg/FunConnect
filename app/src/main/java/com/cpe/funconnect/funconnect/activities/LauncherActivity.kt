package com.cpe.funconnect.funconnect.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.SPLASH_DELAY
import com.google.firebase.messaging.FirebaseMessaging


class LauncherActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var nextIntent: Intent? = null

    /**
     * Handles the first activity selection
     */
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            FirebaseMessaging.getInstance().isAutoInitEnabled = true
            if (this.getSharedPreferences("_", Context.MODE_PRIVATE).getString(
                    EnvironmentVariables.MAIL,
                    "empty") != "empty") {
                nextIntent = Intent(applicationContext, IdleActivity::class.java)
            } else {
                // Replace with FormActivity once the server is online
                nextIntent = Intent(applicationContext, FormActivity::class.java)
            }
            startActivity(nextIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}
