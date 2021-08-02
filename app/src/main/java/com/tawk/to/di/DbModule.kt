package com.tawk.to.di

import android.content.Context
import androidx.room.Room
import com.tawk.to.db.TawkDatabase
import com.tawk.to.db.dao.NoteDao
import com.tawk.to.db.dao.ProfileDao
import com.tawk.to.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideTawkDatabase(@ApplicationContext context: Context): TawkDatabase {
        return Room
            .databaseBuilder(
                context,
                TawkDatabase::class.java,
                TawkDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getUserDao(tawkDatabase: TawkDatabase): UserDao {
        return tawkDatabase.getUsersDao()
    }

    @Singleton
    @Provides
    fun getProfileDao(tawkDatabase: TawkDatabase): ProfileDao {
        return tawkDatabase.getProfileDao()
    }

    @Singleton
    @Provides
    fun getNoteDao(tawkDatabase: TawkDatabase): NoteDao {
        return tawkDatabase.getNoteDao()
    }
}