package com.cpe.funconnect.funconnect.model

class Signature: Traces {

    var abs : ArrayList<Int>
    var ord : ArrayList<Int>
    var time : ArrayList<Int>

    constructor(){
        abs = ArrayList()
        ord = ArrayList()
        time = ArrayList()
    }

    fun clear(){
        this.clear()
    }

    override fun addCoord(abs : Int, ord : Int, time : Int ){
        this.abs.add(abs)
        this.ord.add(ord)
        this.time.add(time)
    }
}