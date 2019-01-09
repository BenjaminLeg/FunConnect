package com.cpe.funconnect.funconnect



import android.os.AsyncTask
import com.github.kittinunf.fuel.httpPost
import org.json.JSONObject


class CommunicationTask() : AsyncTask<Void,Void,String>() {

    private lateinit var jsonObject : JSONObject
    private lateinit var connection : ConnectionInterface


    constructor(jsonObject: JSONObject, connectionInterface: ConnectionInterface) : this() {
        this.jsonObject = jsonObject
        this.connection = connectionInterface
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Void?): String {
        var reply = ""
        "http://httpbin.org/post"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(this.jsonObject.toString())
            .response{
                request, response, result ->
                 reply = response.responseMessage

        }

        while(reply == ""){

        }

        return reply
    }

    override fun onPostExecute(result: String?) {
            connection.onLastReply(result)
    }
}