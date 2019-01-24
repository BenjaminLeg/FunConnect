package com.cpe.funconnect.funconnect.activities

interface ConnectionInterface {
    /**
     * Handles replies from the server to (in)validate request
     */
    fun onPostReply(success : Boolean)
}