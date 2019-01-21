package com.cpe.funconnect.funconnect.task



import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_SERVER
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject
import java.lang.Exception


class RegisterTask() : AsyncTask<Void,Void,Boolean>() {

    private lateinit var jsonObject : JSONObject
    private lateinit var connection : ConnectionInterface


    constructor(jsonObject: JSONObject, connectionInterface: ConnectionInterface) : this() {
        this.jsonObject = jsonObject
        this.connection = connectionInterface
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        var reply = false
        try {
            Log.d(TAG, "Sent data: ${this.jsonObject.toString()}")
            val (request, response, result) = URL_SERVER
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(this.jsonObject.toString())
                .response()

            Log.d(TAG, "Request : ${request.toString()}")
            Log.d(TAG, "Result: ${response.toString()}")

            when (result) {
                is Result.Failure -> {
                    answer = response.statusCode.toString()
                    reply = false
                }
                is Result.Success -> {
                    reply = true
                }
            }
        }catch (e : Exception){
            Log.d(TAG, "Error: ${e.message}")
            answer = "An error occurred during connection to server"
        }finally{
            return reply
        }
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