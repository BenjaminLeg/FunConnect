package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class emailExists (
    var email:String,
    var exists:Boolean
){
    class Deserializer : ResponseDeserializable<Array<emailExists>> {
        override fun deserialize(content: String): Array<emailExists>
                = Gson().fromJson(content, Array<emailExists>::class.java)
    }
}