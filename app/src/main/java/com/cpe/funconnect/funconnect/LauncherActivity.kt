package com.cpe.funconnect.funconnect

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging


class LauncherActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            FirebaseMessaging.getInstance().isAutoInitEnabled = true
            FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener { task ->
                    var msg = "OK"
                    if (!task.isSuccessful) {
                        msg = "NOT OK"
                    }
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
            val intent = Intent(applicationContext, Entry_Activity::class.java)
            startActivity(intent)
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
