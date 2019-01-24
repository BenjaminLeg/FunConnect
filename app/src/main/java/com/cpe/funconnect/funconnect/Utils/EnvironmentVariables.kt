package com.cpe.funconnect.funconnect.utils

class EnvironmentVariables {
    companion object {
        // START Construct URL
        private const val BASE_URL = "http://192.168.137.136:"
        private const val PORT_SERVER_AUTH = "9090/auth"
        private const val PORT_SERVER_PY = "9090/py"
        private const val REGISTER = "/register"
        private const val USER_EXISTS = "/userExists/"
        private const val CONNECT = "/checkAuth"
        private const val VERIFY = "/status/"
        // END construct URL

        // START All URL used
        const val URL_PYTHON = BASE_URL + PORT_SERVER_PY + CONNECT
        const val URL_SERVER = BASE_URL + PORT_SERVER_AUTH + REGISTER
        const val URL_EMAIL = BASE_URL + PORT_SERVER_AUTH + USER_EXISTS
        const val URL_VERIFY = BASE_URL + PORT_SERVER_PY + VERIFY
        const val URL_GET_TEST = "https://httpbin.org/get"
        const val URL_POST_TEST = "https://httpbin.org/post"
        // END All URL used

        // START Variables
        const val MAX_ATTEMPT_CONNECT = 3
        const val ATTEMPT_REGISTER = 5
        const val SPLASH_DELAY: Long = 3000 //3 seconds
        // END Variables

        // START Answers
        const val EMAIL_EXISTS = "Email already exists"
        const val MAIL = "mail"
        const val EMAIL = "email"
        const val PAGE_NOT_FOUND = "Page not found"
        const val BAD_REQUEST = "Bad request"
        const val INT_ISSUE = "Internal Issue"
        const val WRONG_ENTRY = "Invalid Signature"
        const val NO_INT = "No Internet Connection"
        const val TRY_AGAIN = "Please Try Again"
        // END Answers
    }
}