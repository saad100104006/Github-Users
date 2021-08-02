package com.tawk.to.db.entity

import androidx.room.*


@Entity(tableName = "users")
data class User(

    // we are using userName as primary key because it's also unique
    // and since profile details api uses userName  it makes more sense to use userName as primaryKey
    @PrimaryKey @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
)


data class UserDetails(
    @Embedded val user: User,

    @Relation(
        parentColumn = "user_name",
        entityColumn = "note_user_name"
    )
    val note: Note?,

    @Relation(
        parentColumn = "user_name",
        entityColumn = "profile_user_name"
    )
    val profile: Profile?
)