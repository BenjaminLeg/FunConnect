package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 * Data returned by the httpGet request on /userExists
 */
data class EmailExists(
    var email: String,
    var exists: Boolean
) {
    class Deserializer : ResponseDeserializable<EmailExists> {
        override fun deserialize(content: String): EmailExists = Gson().fromJson(content, EmailExists::class.java)
    }
}