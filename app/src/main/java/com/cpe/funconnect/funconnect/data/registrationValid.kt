package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class registrationValid( var isRegistrationValid : Boolean) {

    class Deserializer : ResponseDeserializable<registrationValid> {
        override fun deserialize(content: String): registrationValid
                = Gson().fromJson(content, registrationValid::class.java)
    }
}