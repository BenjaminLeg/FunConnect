package com.cpe.funconnect.funconnect.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 * Data returned by the httpPost request on /checkAuth
 */
data class IdReturn(
    var taskid: String
) {
    class Deserializer : ResponseDeserializable<IdReturn> {
        override fun deserialize(content: String): IdReturn = Gson().fromJson(content, IdReturn::class.java)
    }
}