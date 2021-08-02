package com.tawk.to.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tawk.to.db.entity.Profile
import com.tawk.to.helper.getValueBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class ProfileDaoTest : DatabaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun updateProfileTest() {

        val userName = "test username"

        val profile = Profile(
            userName = userName,
            name = "name",
            company = "company",
            blog = "blog",
            email = "email",
            followers = 100,
            following = 200
        )
        appDatabase.getProfileDao().upsertProfile(profile)
        val updatedProfile = profile.copy(name = "Updated name")
        appDatabase.getProfileDao().upsertProfile(updatedProfile)

        assertEquals(appDatabase.getProfileDao().selectProfileByUserName(userName).getValueBlocking()?.name, "Updated name")
    }
}