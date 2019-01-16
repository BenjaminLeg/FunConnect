package com.cpe.funconnect.funconnect.task

import android.os.AsyncTask
import android.util.Log
import com.cpe.funconnect.funconnect.activities.ConnectionInterface
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
        var message = ""

        val (_, response, result) = URL_PYTHON
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(this.jsonObject.toString())
            .response()

        Log.d(TAG, "Result: ${result.toString()}")

        when(result){
            is Result.Failure ->{
                answer = result.getException().toString()
                reply = false
            }
            is Result.Success ->{
                val token = response.data.get(0)
                while (message != "verified" || answer != "No Internet connexion"){
                    val (_, responseJWT, resultJWT) = URL_PYTHON
                        .httpPost()
                        .header("Content-Type" to "application/json",
                            "JWT" to token)
                        .response()

                    when(resultJWT) {
                        is Result.Failure -> {
                            answer = "No Internet connexion"
                            reply = false
                        }
                        is Result.Success -> {
                            message = responseJWT.data.get(0).toString()
                            reply = true
                            if(message != "verified"){
                                sleep(500)
                            }
                        }
                    }
                }
            }
        }

        return reply
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        connection.onLastReply(result)
    }

    companion object {
        val URL_PYTHON = "http://httpbin.org/post"
        private const val TAG = "RegisterTask"
        var answer : String? = null
    }

}
