package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 * Data returned by the httpGet request on /status
 */
data class PythonReturn(
    var state: String,
    var isAuthValid: Boolean
) {
    class Deserializer : ResponseDeserializable<PythonReturn> {
        override fun deserialize(content: String): PythonReturn = Gson().fromJson(content, PythonReturn::class.java)
    }

}