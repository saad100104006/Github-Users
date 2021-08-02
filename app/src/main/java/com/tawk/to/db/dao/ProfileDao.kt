package com.tawk.to.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.Profile
import com.tawk.to.db.entity.UserDetails
import io.reactivex.rxjava3.core.Completable


@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertProfile(profile: Profile)

    @Query("SELECT * FROM profiles WHERE profile_user_name = :userName")
    fun selectProfileByUserName(userName: String): LiveData<Profile>
}