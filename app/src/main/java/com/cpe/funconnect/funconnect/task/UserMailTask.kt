package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_EMAIL
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class UserMailTask constructor(private val mEmail: String, private val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    /**
     * GET request to the server for checking Email validity
     */
    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false
        val (_, response, result)= (URL_EMAIL + mEmail)
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

        Log.d(TAG, "Response : ${response.statusCode.toString()}")

        when (result) {
            is Result.Failure -> {
                reply = true
                answer = response.statusCode.toString()
            }
            is Result.Success -> {
                    reply = false
                    answer = "Email already exists"
            }


        }
        return reply
    }

    /**
     * Calls connectionInterface method post results
     */
    override fun onPostExecute(success: Boolean?) {
        super.onPostExecute(success)
        connectionInterface.onLastReply(success!!)
    }

    companion object {
        private const val TAG = "UserMailTask"
        var answer : String? = null
    }

}