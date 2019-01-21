package com.cpe.funconnect.funconnect.model


class User: Users {

    private var attempt : Int = 1
    private var signatures: ArrayList<Traces>? = null
    private val email: String
    private val token: String

    constructor(email: String, token: String){
        this.email = email
        this.token = token
        signatures = ArrayList<Traces>()
    }

    override fun addSignature(signature : Signature){
        signatures?.add(signature)
    }

    override fun getAttempt(): Int{
        return this.attempt
    }

    override fun addAttempt(){
        this.attempt++
    }

    override fun getEmail(): String? {
        return this.email
    }
}