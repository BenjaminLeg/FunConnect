package com.cpe.funconnect.funconnect

import android.os.AsyncTask
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class UserLoginTask constructor(private val mEmail: String, val connectionInterface: ConnectionInterface) : AsyncTask<Void, Void, Boolean>() {

    override fun doInBackground(vararg params: Void): Boolean? {
        var reply = false

        val (request, response, result)= "http://httpbin.org/get"
            .httpGet()
            .response()


        when(result){
            is Result.Failure -> {
                reply = false
            }
            is Result.Success -> {
                reply = true
            }
        }

        return reply
    }

    override fun onPostExecute(success: Boolean?) {
        super.onPostExecute(success)
        connectionInterface.onLastReply(success!!)
    }

}