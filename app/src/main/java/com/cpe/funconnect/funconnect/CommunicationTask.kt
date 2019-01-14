package com.cpe.funconnect.funconnect



import android.os.AsyncTask
import android.util.Log
import com.github.kittinunf.fuel.httpPost
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
        var reply = ""
        "http://httpbin.org/post"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(this.jsonObject.toString())
            .response{
                request, response, result ->

                when(result){
                    is com.github.kittinunf.result.Result.Failure ->{
                        reply = "Failure"
                    }
                    is com.github.kittinunf.result.Result.Success ->{
                        reply = "Success"
                    }
                }

        }

        while(reply.isEmpty()){ }

        return true
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        connection.onLastReply(result)
    }
}