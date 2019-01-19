package com.cpe.funconnect.funconnect.model


class User {

    private var attempt : Int = 1
    private var signatures: ArrayList<Signature>? = null
    private val email: String
    private val token: String

    constructor(email: String, token: String){
        this.email = email
        this.token = token
        signatures = ArrayList<Signature>()
    }

    fun addSignature(signature : Signature){
        signatures?.add(signature)
    }

    fun getAttempt(): Int{
        return this.attempt
    }

    fun addAttempt(){
        this.attempt++
    }

    fun getEmail(): String? {
        return this.email
    }
}