package com.tawk.to.network.mapper

import com.google.gson.JsonArray
import com.tawk.to.db.entity.User
import com.tawk.to.ktx.getIntegerValue
import com.tawk.to.ktx.getStringValue
import javax.inject.Inject

class UsersResponseMapper @Inject constructor() :
    NetworkResponseMapper<List<User>, JsonArray> {
    override fun modelFromEntity(entity: JsonArray): List<User> {
        return entity.map {
            val obj = it.asJsonObject
            val userName = obj.getStringValue("login")
            val id = obj.getIntegerValue("id")
            val avatarUrl = obj.getStringValue("avatar_url")

            User(userName = userName, id = id, avatarUrl = avatarUrl)
        }
    }


}