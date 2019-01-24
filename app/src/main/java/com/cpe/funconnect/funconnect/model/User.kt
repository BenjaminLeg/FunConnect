package com.cpe.funconnect.funconnect.model


class User(private val email: String, private val token: String) : Users {
    private var attempt: Int = 1
    private var signatures: ArrayList<Traces>? = ArrayList<Traces>()

    override fun addSignature(signature: Signature) {
        signatures?.add(signature)
    }

    override fun getAttempt(): Int {
        return this.attempt
    }

    override fun addAttempt() {
        this.attempt++
    }

    override fun getEmail(): String? {
        return this.email
    }

    override fun getSignature(indice: Int): Traces {
        return signatures!![indice]
    }
}