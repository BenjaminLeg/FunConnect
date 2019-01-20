package com.cpe.funconnect.funconnect.utils

import android.content.Context
import android.widget.Toast

class Utils {

    companion object {
        fun handleError(context: Context, answer: String?) {
            when (answer) {
                "404" -> {
                    Toast.makeText(context, "Internet issue", Toast.LENGTH_LONG).show()
                }
                "400" -> {
                    Toast.makeText(context, "Bad request", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(context, "Internal issue", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}