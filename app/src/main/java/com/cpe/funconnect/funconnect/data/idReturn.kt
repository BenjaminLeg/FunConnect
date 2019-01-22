package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class idReturn(
    var taskid : String
) {
    class Deserializer : ResponseDeserializable<idReturn> {
        override fun deserialize(content: String): idReturn
                = Gson().fromJson(content, idReturn::class.java)
    }
}