package com.tawk.to.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey @ColumnInfo(name = "note_user_name") val userName: String,
    @ColumnInfo(name = "note") val note: String,
)
