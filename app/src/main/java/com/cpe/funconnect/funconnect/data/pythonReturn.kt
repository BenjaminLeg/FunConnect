package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class pythonReturn(
    var status : String,
    var isAuthOK : Boolean
) {
    class Deserializer : ResponseDeserializable<pythonReturn> {
        override fun deserialize(content: String): pythonReturn
                = Gson().fromJson(content, pythonReturn::class.java)
    }

}