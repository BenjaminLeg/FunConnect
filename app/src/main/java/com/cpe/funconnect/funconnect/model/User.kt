package com.cpe.funconnect.funconnect.model


class User {

    private var attempt : Int = 1
    private var signatures: ArrayList<Signature>? = null
    private var email: String? = null
    private var token: String? = null

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