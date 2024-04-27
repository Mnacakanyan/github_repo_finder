package com.nairi.githubrepofinder.utils

import java.net.UnknownHostException

fun Exception.toErrorMessage(): String {
    return when(this) {
        is UnknownHostException -> "No connection"
        else -> "Unknown error"
    }
}