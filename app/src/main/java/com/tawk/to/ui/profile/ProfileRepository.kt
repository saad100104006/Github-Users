package com.tawk.to.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import com.tawk.to.base.BaseRepository
import com.tawk.to.db.dao.NoteDao
import com.tawk.to.db.dao.ProfileDao
import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.network.Api
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: Api,
    private val userDao: UserDao,
    private val profileDao: ProfileDao,
    private val noteDao: NoteDao
) : BaseRepository() {

    fun getUserDetails(userName: String): LiveData<UserDetails> {
        return userDao.selectUserLiveDataByUserName(userName)
    }

    fun updateProfile(userName: String) {
        api.getUserDetails(userName)
            .flatMapCompletable {
                Completable.fromAction {
                    profileDao.upsertProfile(it)
                }
            }
            .subscribe({
                Log.d(TAG, "updateProfile: Profile updated")
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    fun updateNote(note: Note) {
        noteDao.upsertNote(note)
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "updateNote: Note updated")
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    companion object {
        private const val TAG = "ProfileRepository"
    }
}