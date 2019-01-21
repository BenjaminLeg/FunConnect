package com.cpe.funconnect.funconnect.controlers

import com.cpe.funconnect.funconnect.model.Signature

interface userRequestControlers {
    fun addSignature(signature : Signature)
    fun getAttempt(): Int
    fun addAttempt()
    fun getEmail(): String?
}