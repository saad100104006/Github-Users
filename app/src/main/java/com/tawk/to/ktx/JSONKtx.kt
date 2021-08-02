package com.tawk.to.ktx

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject


fun JsonObject.getStringValue(key: String): String {
    return try {
        if (has(key)) if (this[key].isJsonNull) "" else this[key].asString else ""
    } catch (e: Exception) {
        Log.d("JsonKtx", "getStringValue: " + e.message)
        ""
    }
}


fun JsonObject.getIntegerValue(key: String): Int {
    return try {
        if (has(key)) if (this[key].isJsonNull) 0 else this[key].asInt else 0
    } catch (e: java.lang.Exception) {
        Log.d("JsonKtx", "getIntegerValue: " + e.message)
        0
    }
}

fun JsonObject.getDoubleValue(key: String): Double {
    return try {
        if (has(key)) if (this[key].isJsonNull) 0.0 else this[key].asDouble else 0.0
    } catch (e: java.lang.Exception) {
        Log.d("JsonKtx", "getDoubleValue: " + e.message)
        0.0
    }
}

fun JsonObject.getArray(key: String): JsonArray {
    val element = get(key)
    return if (element.isJsonObject) {
        val obj = element.asJsonObject
        JsonArray().apply {
            add(obj)
        }
    } else {
        element.asJsonArray
    }
}

fun List<JsonObject>.toJsonArray(): JsonArray {
    val jsonArray = JsonArray()
    forEach {
        jsonArray.add(it)
    }

    return jsonArray
}