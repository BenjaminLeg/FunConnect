package com.cpe.funconnect.funconnect

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.firebase.messaging.FirebaseMessagingService

import kotlinx.android.synthetic.main.activity_form.*

/**
 * A login screen that offers login via email/password.
 */
class FormActivity : AppCompatActivity(){
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null
    private var context: Context = this

    protected fun getContext(): Context{
        return this.context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        // Set up the login form.
        email_sign_in_button.setOnClickListener { attemptLogin() }
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        email.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            mAuthTask = UserLoginTask(emailStr)
            mAuthTask!!.execute(null as Void?)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }


    object ProfileQuery {
        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY
        )
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val mEmail: String) :
        AsyncTask<Void, Void, Boolean>() {

        // creates a unique boundary based on time stamp
        private val url = "https://www.google.com"
        val queue = Volley.newRequestQueue(applicationContext)

        override fun doInBackground(vararg params: Void): Boolean? {
            var reply = false
            "http://httpbin.org/post"
                .httpGet()
                .header("Content-Type" to "application/json")
                .body(this.mEmail)
                .response{
                        request, response, result ->

                    when(result){
                        is com.github.kittinunf.result.Result.Failure ->{
                            Toast.makeText(applicationContext, response.responseMessage,Toast.LENGTH_LONG).show()
                        }
                        is com.github.kittinunf.result.Result.Success ->{
                            Toast.makeText(applicationContext, response.responseMessage,Toast.LENGTH_LONG).show()
                        }
                    }

                }

            return true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            showProgress(false)

            if (success!!) {
                val intent = Intent(getContext(), DrawRegister::class.java)
                getSharedPreferences("_", FirebaseMessagingService.MODE_PRIVATE).edit().putString("mail", mEmail).apply();
                intent.putExtra("email", mEmail)
                startActivity(intent)
            } else {
                email.error = getString(R.string.error_invalid_email)
                email.requestFocus()
            }
        }

    }
}
