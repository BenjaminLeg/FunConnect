package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.Utils.EnvironmentVariables.Companion.URL_EMAIL
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.NullPointerException

class UserMailTask constructor(private val mEmail: String, private val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false

        val (_, response, result)= URL_EMAIL
            .httpGet()
            .response()

        try {
            val hello = response.headers.get("Connection").toString()
            Log.d(TAG, "Result: ${hello}")

            when (result) {
                is Result.Failure -> {
                    reply = false
                    answer = response.statusCode.toString()
                }
                is Result.Success -> {
                    //Replace if condition with the correct expected output
                    if (hello == URL_EMAIL) {
                        reply = true
                    } else {
                        reply = false
                        answer = "Email already exists"
                    }

                }
            }
        }catch (e : Exception){
            Log.d(TAG, "Erreur null pointer : "+ e.toString())
        }
        return reply
    }

    override fun onPostExecute(success: Boolean?) {
        super.onPostExecute(success)
        connectionInterface.onLastReply(success!!)
    }

    companion object {
        private const val TAG = "UserMailTask"
        var answer : String? = null
    }

}