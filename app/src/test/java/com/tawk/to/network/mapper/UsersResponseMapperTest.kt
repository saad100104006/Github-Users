package com.tawk.to.network.mapper

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import junit.framework.TestCase
import org.junit.Test

class UsersResponseMapperTest : TestCase() {

    val usersResponseMapper: UsersResponseMapper = UsersResponseMapper()

    @Test
    fun testTest() {

        val data = JsonArray().apply {
            add(
                JsonObject().apply {
                    addProperty("login", "mojombo")
                    addProperty("id", 1)
                    addProperty("node_id", "MDQ6VXNlcjE=")
                    addProperty("avatar_url", "https://avatars.githubusercontent.com/u/1?v=4")
                    addProperty("gravatar_id", "")
                    addProperty("url", "https://api.github.com/users/mojombo")
                    addProperty("html_url", "https://github.com/mojombo")
                    addProperty("followers_url", "https://api.github.com/users/mojombo/followers")
                    addProperty("following_url", "https://api.github.com/users/mojombo/following{/other_user}")
                    addProperty("gists_url", "https://api.github.com/users/mojombo/gists{/gist_id}")
                    addProperty("starred_url", "https://api.github.com/users/mojombo/starred{/owner}{/repo}")
                    addProperty("subscriptions_url", "https://api.github.com/users/mojombo/subscriptions")
                    addProperty("organizations_url", "https://api.github.com/users/mojombo/orgs")
                    addProperty("repos_url", "https://api.github.com/users/mojombo/repos")
                    addProperty("events_url", "https://api.github.com/users/mojombo/events{/privacy}")
                    addProperty("received_events_url", "https://api.github.com/users/mojombo/received_events")
                    addProperty("type", "User")
                    addProperty("site_admin", false)
                }
            )
        }


        assertEquals(
            usersResponseMapper.modelFromEntity(data).size,
            1
        )

        assertEquals(
            usersResponseMapper.modelFromEntity(data)[0].userName,
            "mojombo"
        )
        assertEquals(
            usersResponseMapper.modelFromEntity(data)[0].userName,
            "mojombo"
        )
    }
}