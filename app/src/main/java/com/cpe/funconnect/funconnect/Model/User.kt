package com.cpe.funconnect.funconnect.Model

import com.cpe.funconnect.funconnect.Model.Signature


class User(protected var signatures: ArrayList<Signature>, protected var email: String, private var token: String) {

    fun addSignature(signature : Signature){
        signatures.add(signature)
    }

}