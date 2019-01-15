package com.cpe.funconnect.funconnect.Activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.cpe.funconnect.funconnect.R

class ValidationScreenActivity: AppCompatActivity()  {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 4000 //4 seconds

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

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