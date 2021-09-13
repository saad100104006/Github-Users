package com.tawk.to.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.tawk.to.db.entity.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private var PAGE_SIZE = 10

    fun getUsers(): Flowable<PagingData<UserDetails>> {
        //increase PAGE_SIZE by 5 everytime getUsers called
        PAGE_SIZE += 5
        return repository
            .getUsersWithMediator(PAGE_SIZE)
            .cachedIn(viewModelScope)
    }
}