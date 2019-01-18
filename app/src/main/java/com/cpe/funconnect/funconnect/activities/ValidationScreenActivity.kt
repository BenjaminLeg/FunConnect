package com.cpe.funconnect.funconnect.activities

import android.graphics.drawable.AnimatedStateListDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.cpe.funconnect.funconnect.R
import kotlinx.android.synthetic.main.activity_validation.*




class ValidationScreenActivity: AppCompatActivity()  {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 4000 //4 seconds
    private var isChecked: Boolean = false
    private lateinit var rocketAnimation: AnimatedVectorDrawable


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
        expandcollapse.apply {
            rocketAnimation = background as AnimatedVectorDrawable
        }

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    override fun onStart() {
        super.onStart()
        rocketAnimation.start()
}


    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }


}


