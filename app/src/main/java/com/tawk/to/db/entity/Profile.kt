package com.tawk.to.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey @ColumnInfo(name = "profile_user_name") val userName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "blog") val blog: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "following") val following: Int,
)