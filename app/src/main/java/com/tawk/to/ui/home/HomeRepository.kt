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
    // getUsersWithMediator fetch the users from UsersRxRemoteMediator
    fun getUsersWithMediator(pageSize:Int): Flowable<PagingData<UserDetails>> {
        return Pager(
            config = PagingConfig(
                pageSize =  pageSize,
                enablePlaceholders = true,
                maxSize = pageSize + 4*pageSize,
                prefetchDistance = 5,
                initialLoadSize = pageSize*2
            ),
            remoteMediator = usersRxRemoteMediator,
            pagingSourceFactory = { userDao.selectAllUserPaginated() }
        ).flowable
    }
}