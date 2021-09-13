package com.tawk.to.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

     val searchQueryLD = MutableLiveData<String>()

    //MediatorLiveData for search, fetch the related data from repository based on search
    val searchedUserLiveData = MediatorLiveData<List<UserDetails>>().apply {
        addSource(searchQueryLD) {
            Completable.fromAction {
                val userList = repository.searchUser(it)
                postValue(userList)
            }.subscribeOn(Schedulers.io()).subscribe({
                Log.d(TAG, "search done: ")
            }, Throwable::printStackTrace)
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        searchQueryLD.postValue(searchQuery)
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}