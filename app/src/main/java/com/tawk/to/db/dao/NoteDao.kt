package com.tawk.to.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tawk.to.db.entity.Note
import io.reactivex.rxjava3.core.Completable


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertNote(note: Note): Completable
}