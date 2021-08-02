package com.tawk.to.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import io.reactivex.rxjava3.core.Completable

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun selectAllUserPaginated(): PagingSource<Int, UserDetails>

    @Transaction
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun selectAllUser(): LiveData<List<UserDetails>>

    @Transaction
    @Query("SELECT * FROM users LEFT JOIN notes ON notes.note_user_name = users.user_name WHERE users.user_name LIKE '%' || :searchQuery || '%' OR notes.note LIKE '%' || :searchQuery || '%' ORDER BY id ASC")
    fun searchUser(searchQuery: String): List<UserDetails>

    @Transaction
    @Query("SELECT * FROM users WHERE user_name = :userName")
    fun selectUserLiveDataByUserName(userName: String): LiveData<UserDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultipleUser(users: List<User>)

    @Query("DELETE FROM users")
    fun clearTable()
}