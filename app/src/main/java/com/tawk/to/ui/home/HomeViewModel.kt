package com.tawk.to.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    fun getUsers(): Flowable<PagingData<UserDetails>> {
        return repository
            .getUsersWithMediator()
            .cachedIn(viewModelScope)
    }
}