package com.tawk.to.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tawk.to.db.entity.User
import com.tawk.to.helper.getValueBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class UserDaoTest : DatabaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertUserTest() {
        val userList = listOf(
            User(
                userName = "test user",
                id = 1,
                avatarUrl = "sampleImageUrl"
            ),
            User(
                userName = "test user 2",
                id = 3,
                avatarUrl = "sampleImageUrl"
            )
        )
        appDatabase.getUsersDao().insertMultipleUser(userList)
        val userListSize = appDatabase.getUsersDao().selectAllUser().getValueBlocking()?.size
        assertEquals(userListSize, 2)
    }


    @Test
    fun searchUserTest() {
        val userList = listOf(
            User(
                userName = "first user",
                id = 1,
                avatarUrl = "sampleImageUrl"
            ),
            User(
                userName = "second user",
                id = 3,
                avatarUrl = "sampleImageUrl"
            ),
            User(
                userName = "Thirst user",
                id = 3,
                avatarUrl = "sampleImageUrl"
            )
        )
        appDatabase.getUsersDao().insertMultipleUser(userList)


        assertEquals(
            appDatabase.getUsersDao().searchUser("cond").size,
            1
        )

        assertEquals(
            appDatabase.getUsersDao().searchUser("rst").size,
            2
        )
        assertEquals(
            appDatabase.getUsersDao().searchUser("").size,
            3
        )
    }
}