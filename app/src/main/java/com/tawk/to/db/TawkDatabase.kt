package com.tawk.to.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tawk.to.db.dao.NoteDao
import com.tawk.to.db.dao.ProfileDao
import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.Profile
import com.tawk.to.db.entity.User

@Database(
    entities = [
        User::class,
        Profile::class,
        Note::class
    ], version = 1
)
abstract class TawkDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UserDao
    abstract fun getProfileDao(): ProfileDao
    abstract fun getNoteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "tawk_database"
    }
}