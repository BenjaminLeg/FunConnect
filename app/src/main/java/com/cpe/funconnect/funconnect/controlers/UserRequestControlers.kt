package com.cpe.funconnect.funconnect.controlers

import com.cpe.funconnect.funconnect.model.Signature
import com.cpe.funconnect.funconnect.model.Traces
import com.cpe.funconnect.funconnect.model.Users

interface UserRequestControlers {
    fun addSignature(signature : Signature)
    fun getAttempt(): Int
    fun addAttempt()
    fun getEmail(): String?
    fun getSignature(indice : Int): Traces
    fun getUser() : Users
}