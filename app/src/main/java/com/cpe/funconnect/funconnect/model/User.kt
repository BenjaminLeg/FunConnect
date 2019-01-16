package com.cpe.funconnect.funconnect.model


class User(protected var signatures: ArrayList<Signature>, protected var email: String, private var token: String) {

    fun addSignature(signature : Signature){
        signatures.add(signature)
    }

}