package com.cpe.funconnect.funconnect

import com.cpe.funconnect.funconnect.Tools.Coord
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class JSONmaker {

    var jsonObject = JSONObject()


    fun addCoord(listeCoord : kotlin.collections.ArrayList<Coord>?, attempt : Int){
        var tmpList = LinkedList<Coord>(listeCoord)
        var iterator = tmpList.iterator()
        var tmpJsonObj = JSONObject()
        var jsonArrayAbs = JSONArray()
        var jsonArrayOrd = JSONArray()
        var jsonArrayTime = JSONArray()
        var jsonArraySig = JSONArray()

        var i = 0
        while(iterator.hasNext()){
            var value = iterator.next()
            i++
            jsonArrayAbs?.put(value.x)
            jsonArrayOrd?.put(value.y)
            jsonArrayTime?.put(value.z)
        }

        tmpJsonObj.put("abs", jsonArrayAbs)
        tmpJsonObj.put("ord", jsonArrayOrd)
        tmpJsonObj.put("time", jsonArrayTime)
        tmpJsonObj.put("signature", tmpJsonObj)
        jsonArraySig.put(attempt, tmpJsonObj)
        jsonObject.put("signatures", jsonArraySig)
    }

    fun getJSON(): JSONObject{
        return jsonObject
    }

    fun addMail(email : String){
        jsonObject.put("email", email)
    }

    fun createUserJson(){

    }


}