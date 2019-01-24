package com.cpe.funconnect.funconnect.controlers

import com.cpe.funconnect.funconnect.model.Signature
import com.cpe.funconnect.funconnect.model.Traces
import com.cpe.funconnect.funconnect.model.User
import com.cpe.funconnect.funconnect.model.Users

class UserRequestControler(email: String, token: String) : UserRequestControlers {
    private var userRegistrationRequest : Users = User(email, token)

    override fun getAttempt(): Int {
        return userRegistrationRequest.getAttempt()
    }

    override fun addAttempt() {
        userRegistrationRequest.addAttempt()
    }

    override fun getEmail(): String? {
        return userRegistrationRequest.getEmail()
    }

    override fun addSignature(signature: Signature) {
        userRegistrationRequest.addSignature(signature)
    }

    override fun getSignature(indice: Int): Traces {
        return userRegistrationRequest.getSignature(indice)
    }

    override fun getUser(): Users {
        return userRegistrationRequest
    }
}