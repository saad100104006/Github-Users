package com.tawk.to.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.paginators.UsersRxRemoteMediator
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class HomeRepository
@Inject constructor(
    private val userDao: UserDao,
    private val usersRxRemoteMediator: UsersRxRemoteMediator,
) {
    fun getUsersWithMediator(): Flowable<PagingData<UserDetails>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = usersRxRemoteMediator,
            pagingSourceFactory = { userDao.selectAllUserPaginated() }
        ).flowable
    }
}