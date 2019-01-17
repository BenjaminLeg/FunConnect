package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.EnvironmentVariables.Companion.URL_EMAIL
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.BufferedReader
import java.io.InputStreamReader

class UserMailTask constructor(private val mEmail: String, private val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false

        val (_, response, result)= URL_EMAIL
            .httpGet()
            .response()

        val hello = BufferedReader(InputStreamReader(response.dataStream))
        Log.d(TAG, "Result: ${hello.readLine()}")


        when(result){
            is Result.Failure -> {
                reply = false
                answer = response.statusCode.toString()
            }
            is Result.Success -> {
                //Replace if condition with the correct expected output
                if(hello.readLine().isNullOrEmpty()){
                    reply = true
                }
                else{
                    reply = false
                    answer = "Email already exists"
                }

            }
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