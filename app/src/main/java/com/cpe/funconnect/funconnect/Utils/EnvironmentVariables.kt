package com.cpe.funconnect.funconnect.utils

class EnvironmentVariables {
    companion object {
        // Construct URL
        const val BASE_URL = "http://192.168.137.1:"
        const val PORT_SERVER_AUTH = "3030"
        const val PORT_SERVER_PY = "5000"
        const val REGISTER = "/register"
        const val USER_EXISTS = "/userExists/"
        const val CONNECT = "/connect"
        const val VERIFY = "/verify"
        // END construct URL
        const val URL_PYTHON = BASE_URL + PORT_SERVER_PY + CONNECT
        const val URL_SERVER = BASE_URL + PORT_SERVER_AUTH + REGISTER
        const val URL_EMAIL = BASE_URL + PORT_SERVER_AUTH + USER_EXISTS
        const val URL_VERIFY = BASE_URL + PORT_SERVER_PY + VERIFY
        const val MAX_ATTEMPT_CONNECT = 3
        const val ATTEMPT_REGISTER = 5
        const val SPLASH_DELAY: Long = 3000 //3 seconds
    }
}