package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class emailExists (
    var email:String,
    var exists:Boolean
){
    class Deserializer : ResponseDeserializable<emailExists> {
        override fun deserialize(content: String): emailExists
                = Gson().fromJson(content, emailExists::class.java)
    }
}