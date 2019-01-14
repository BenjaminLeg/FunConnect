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

    fun addCoord(coord : Coord){
        abs.add(coord.x)
        ord.add(coord.y)
        time.add(coord.z)
    }
}