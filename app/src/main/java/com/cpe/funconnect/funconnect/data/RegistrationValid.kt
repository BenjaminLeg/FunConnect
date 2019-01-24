package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 * Data returned by the httpPost request on /register
 */
data class RegistrationValid(var isRegistrationValid : Boolean) {

    class Deserializer : ResponseDeserializable<RegistrationValid> {
        override fun deserialize(content: String): RegistrationValid
                = Gson().fromJson(content, RegistrationValid::class.java)
    }
}