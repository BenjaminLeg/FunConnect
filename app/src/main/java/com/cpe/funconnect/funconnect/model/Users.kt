package com.cpe.funconnect.funconnect.model

interface Users {
    fun addSignature(signature : Signature)
    fun getAttempt(): Int
    fun addAttempt()
    fun getEmail(): String?
}