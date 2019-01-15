package com.cpe.funconnect.funconnect.Task



import android.os.AsyncTask
import com.cpe.funconnect.funconnect.Activities.ConnectionInterface
import com.cpe.funconnect.funconnect.R
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject


class CommunicationTask() : AsyncTask<Void,Void,Boolean>() {

    private lateinit var jsonObject : JSONObject
    private lateinit var connection : ConnectionInterface


    constructor(jsonObject: JSONObject, connectionInterface: ConnectionInterface) : this() {
        this.jsonObject = jsonObject
        this.connection = connectionInterface
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        var reply = false
        val (request, response, result) = URL_PYTHON
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(this.jsonObject.toString())
            .response()

        when(result){
            is Result.Failure ->{
                reply = false
            }
            is Result.Success ->{
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
        val URL_PYTHON = "http://httpbin.org/post"
        val URL_SERVER = "http://httpbin.org/post"
    }
}