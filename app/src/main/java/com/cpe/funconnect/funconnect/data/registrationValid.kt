package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class registrationValid( var isRegistrationValid : Boolean) {

    class Deserializer : ResponseDeserializable<Array<registrationValid>> {
        override fun deserialize(content: String): Array<registrationValid>
                = Gson().fromJson(content, Array<registrationValid>::class.java)
    }
}