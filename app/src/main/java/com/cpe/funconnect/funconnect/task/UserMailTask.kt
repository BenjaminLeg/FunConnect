package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.cpe.funconnect.funconnect.data.emailExists
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_EMAIL
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class UserMailTask constructor(private val mEmail: String, private val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    /**
     * GET request to the server for checking Email validity
     */
    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false
        try {
            val (request, response, result)= (URL_EMAIL + mEmail)
                .httpGet()
                .responseObject(emailExists.Deserializer())
            Log.d(TAG, request.toString())
            Log.d(TAG, response.toString())
            Log.d(TAG, result.component1()?.exists.toString())
            reply = handleAnswer(response, result)
        }catch (e : Exception){
            Log.d(TAG, "Error occurred : "+ e.toString())
        }finally {
            return reply
        }
    }

    /**
     * Checks status code
     * If 200 -> Checks to body of the reply
     */
    private fun handleAnswer(response : Response, result: Result<emailExists, Exception>) : Boolean {

        var retour = result.component1()!!.exists
        var reply = false

        when (result) {
            is Result.Failure -> {
                reply = false
                answer = response.statusCode.toString()
            }
            is Result.Success -> {
                //Replace if condition with the correct expected output
                if (retour) {
                    reply = false
                    answer = "Email already exists"
                } else {
                    reply = true
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