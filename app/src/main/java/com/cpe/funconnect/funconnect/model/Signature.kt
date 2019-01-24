package com.cpe.funconnect.funconnect.model

class Signature() : Traces {

    var abs: ArrayList<Int> = ArrayList()
    var ord: ArrayList<Int> = ArrayList()
    var time: ArrayList<Int> = ArrayList()

    override fun addCoord(abs: Int, ord: Int, time: Int) {
        this.abs.add(abs)
        this.ord.add(ord)
        this.time.add(time)
    }
}