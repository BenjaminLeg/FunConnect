package com.cpe.funconnect.funconnect.task



import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.utils.EnvironmentVariables.Companion.URL_SERVER
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject


class RegisterTask() : AsyncTask<Void,Void,Boolean>() {

    private lateinit var jsonObject : JSONObject
    private lateinit var connection : ConnectionInterface


    constructor(jsonObject: JSONObject, connectionInterface: ConnectionInterface) : this() {
        this.jsonObject = jsonObject
        this.connection = connectionInterface
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        var reply = false

        Log.d(TAG, "Sent data: ${this.jsonObject.toString()}")
        val (request, response, result) = "http://localhost:3030/register"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(this.jsonObject.toString())
            .response()

        Log.d(TAG, "Request : ${request.toString()}")
        Log.d(TAG, "Result: ${response.toString()}")

        when(result){
            is Result.Failure ->{
                answer = result.getException().toString()
                reply = false
            }
            is Result.Success -> {
                reply = true
            }
        }

        return reply
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        connection.onLastReply(result)
    }

    companion object {
        private const val TAG = "RegisterTask"
        var answer : String? = null
    }
}