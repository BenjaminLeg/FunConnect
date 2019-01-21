package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_PYTHON
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_VERIFY
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject
import java.lang.Thread.sleep

class ConnectTask() : AsyncTask<Void, Void, Boolean>(){


    private lateinit var jsonObject : JSONObject
    private lateinit var connection : ConnectionInterface

    constructor(jsonObject: JSONObject, connectionInterface: ConnectionInterface) : this() {
        this.jsonObject = jsonObject
        this.connection = connectionInterface
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        var reply = false

        try{
            //Ask for token while sending signature
            val (_, response, result) = URL_PYTHON
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(this.jsonObject.toString())
                .response()

            Log.d(TAG, "Result: ${result.toString()}")

            when(result){
                is Result.Failure ->{
                    answer = response.statusCode.toString()
                    reply = false
                }
                is Result.Success ->{
                    try{
                        val token = response.data.get(0).toString()
                        reply = verificationReturn(token)
                    }catch (ex : Exception){
                        Log.d(TAG, "Error while opening token : " + ex.toString())
                    }
                }
            }
        }catch (e : java.lang.Exception){
            Log.d(TAG, "Error occurred : " + e.toString())
        }finally {
            return reply
        }

    }

    private fun verificationReturn(token: String): Boolean {
        //Spamming Python server to get the reply
        var message = ""
        var reply = false
        while (message != "verified" && answer != "No internet connection"){
            val (_, responseJWT, resultJWT) = URL_VERIFY
                .httpGet()
                .header("Content-Type" to "application/json",
                    "JWT" to token)
                .response()

            when(resultJWT) {
                is Result.Failure -> {
                    answer = "No internet connection"
                    reply = false
                }
                is Result.Success -> {
                    message = responseJWT.data.toString()
                    if(!message.contains("verified")){
                        sleep(500)
                    }
                    else{
                        reply = checkResponse(message)
                    }
                }
            }
        }
        return reply
    }

    private fun checkResponse(message: String) : Boolean {
        var reply = false
        if(message.contains("true")){
            reply = true
        }
        else{
            answer = "invalid entry"
        }
        return reply
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        connection.onPostReply(result)
    }

    companion object {
        private const val TAG = "RegisterTask"
        var answer : String? = null
    }

}
