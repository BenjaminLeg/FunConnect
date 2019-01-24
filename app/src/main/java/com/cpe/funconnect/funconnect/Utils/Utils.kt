package com.cpe.funconnect.funconnect.utils

import android.content.Context
import android.widget.Toast

class Utils {

    companion object {
        fun handleError(context: Context, answer: String?) {
            when (answer) {
                "404" -> {
                    Toast.makeText(context, EnvironmentVariables.PAGE_NOT_FOUND, Toast.LENGTH_LONG).show()
                }
                "400" -> {
                    Toast.makeText(context, EnvironmentVariables.BAD_REQUEST, Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(context, EnvironmentVariables.INT_ISSUE, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}