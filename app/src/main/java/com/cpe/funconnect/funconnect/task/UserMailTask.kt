package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class UserMailTask constructor(private val mEmail: String, private val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    /**
     * GET request to the server for checking Email validity
     */
    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false
        val (_, response, result)= ("http://10.0.2.2:3030/userExists/" + mEmail)
            .httpGet()
            .response()

        try {
            reply = handleAnswer(response, result)
        }catch (e : Exception){
            Log.d(TAG, "Error null pointer : "+ e.toString())
        }

        return reply
    }

    /**
     * Checks status code
     * If 200 -> Checks to body of the reply
     */
    private fun handleAnswer(response : Response, result: Result<Any, Exception>) : Boolean {

        var hello = response.toString()
        var reply = false

        Log.d(TAG, "Response : $hello")

        when (result) {
            is Result.Failure -> {
                reply = false
                answer = response.statusCode.toString()
            }
            is Result.Success -> {
                //Replace if condition with the correct expected output
                if (hello.contains("false")) {
                    reply = true
                } else {
                    reply = false
                    answer = "Email already exists"
                }

            }
        }
        return reply
    }

    /**
     * Calls connectionInterface method post results
     */
    override fun onPostExecute(success: Boolean?) {
        super.onPostExecute(success)
        connectionInterface.onPostReply(success!!)
    }

    companion object {
        private const val TAG = "UserMailTask"
        var answer : String? = null
    }

}