package com.cpe.funconnect.funconnect.Tools

class Signature {

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

    fun addCoord(abs : Int, ord : Int, time : Int ){
        this.abs.add(abs)
        this.ord.add(ord)
        this.time.add(time)
    }
}