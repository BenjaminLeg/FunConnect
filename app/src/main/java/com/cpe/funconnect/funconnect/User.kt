package com.cpe.funconnect.funconnect

import com.cpe.funconnect.funconnect.Tools.Signature


class User(protected var signatures: ArrayList<Signature>, protected var email: String, private var token: String) {

    fun addSignature(signature : Signature){
        signatures.add(signature)
    }

}