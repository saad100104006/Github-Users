package com.tawk.to.network.mapper

import com.google.gson.JsonObject
import com.tawk.to.db.entity.Profile
import com.tawk.to.ktx.getIntegerValue
import com.tawk.to.ktx.getStringValue
import javax.inject.Inject

class UserDetailsResponseMapper @Inject constructor() :
    NetworkResponseMapper<Profile, JsonObject> {
    override fun modelFromEntity(entity: JsonObject): Profile {
        val userName = entity.getStringValue("login")
        val name = entity.getStringValue("name")
        val company = entity.getStringValue("company")
        val blog = entity.getStringValue("blog")
        val email = entity.getStringValue("email")
        val followers = entity.getIntegerValue("followers")
        val following = entity.getIntegerValue("following")

        return Profile(
            userName = userName,
            name = name,
            company = company,
            blog = blog,
            email = email,
            followers = followers,
            following = following,
        )
    }
}