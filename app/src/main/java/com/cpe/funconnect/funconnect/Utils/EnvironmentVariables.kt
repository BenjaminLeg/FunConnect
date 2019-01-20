package com.cpe.funconnect.funconnect.utils

class EnvironmentVariables {
    companion object {
        const val URL_PYTHON = "http://localhost:3030/register"
        const val URL_SERVER = "10.0.2.2:3030/register"
        const val URL_EMAIL = "10.0.2.2:3030/userExists/"
        const val URL_VERIFY = "http://localhost:3030/register"
        const val MAX_ATTEMPT_CONNECT = 3
        const val ATTEMPT_REGISTER = 5
        const val SPLASH_DELAY: Long = 3000 //3 seconds
    }
}