package com.tawk.to.ktx

import java.lang.Exception


fun safeExecute(task: () -> Unit) {
    try {
        task.invoke()
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}