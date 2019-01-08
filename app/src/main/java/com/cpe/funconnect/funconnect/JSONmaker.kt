package com.cpe.funconnect.funconnect

import com.cpe.funconnect.funconnect.Tools.Coord
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class JSONmaker {

    private var jsonObject = JSONObject()


    fun createJSON(listeCoord : kotlin.collections.ArrayList<Coord>?){
        var tmpList = LinkedList<Coord>(listeCoord)
        var iterator = tmpList.iterator()
        var jsonArrayX = JSONArray()
        var jsonArrayY = JSONArray()
        var jsonArrayZ = JSONArray()
        var i = 0
        while(iterator.hasNext()){
            var value = iterator.next()
            i++
            jsonArrayX?.put(value.x)
            jsonArrayY?.put(value.y)
            jsonArrayZ?.put(value.z)
        }

        jsonObject.put("x", jsonArrayX)
        jsonObject.put("y", jsonArrayY)
        jsonObject.put("z", jsonArrayZ)
    }


}