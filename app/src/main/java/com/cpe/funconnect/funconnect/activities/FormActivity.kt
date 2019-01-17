package com.cpe.funconnect.funconnect.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.content.Intent
import android.widget.Toast
import com.cpe.funconnect.funconnect.R
import com.cpe.funconnect.funconnect.task.UserMailTask
import com.cpe.funconnect.funconnect.task.UserMailTask.Companion.answer
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_form.*

/**
 * A login screen that offers login via email/password.
 */
class FormActivity : AppCompatActivity(), ConnectionInterface {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserMailTask? = null


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


            mAuthTask = UserMailTask(emailStr, this)
            mAuthTask!!.execute(null as Void?)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    override fun onLastReply(success: Boolean) {
        mAuthTask = null
        showProgress(false)

        if (success) {
            val intent = Intent(this, DrawRegister::class.java)
            getSharedPreferences("_", FirebaseMessagingService.MODE_PRIVATE).edit().putString("mail", email.text.toString()).apply();
            intent.putExtra("email", email.text.toString())
            startActivity(intent)
            finish()

        } else {
            handleError(answer)
        }
    }

    private fun handleError(answer: String?) {
        when(answer){
            "404" -> {Toast.makeText(this, "Internet issue", Toast.LENGTH_LONG).show()}
            "400" -> {Toast.makeText(this, "Bad request", Toast.LENGTH_LONG).show()}
            "Email already exists" -> {email.error = UserMailTask.answer
                email.requestFocus()}
            else -> {Toast.makeText(this, "Internal issue", Toast.LENGTH_LONG).show()}
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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
    }

    companion object {
        @JvmStatic val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    }

}
