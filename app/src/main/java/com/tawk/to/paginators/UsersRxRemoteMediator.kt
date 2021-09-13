package com.tawk.to.paginators

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.tawk.to.db.dao.UserDao
import com.tawk.to.network.Api
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import io.reactivex.rxjava3.core.Completable



class UsersRxRemoteMediator @Inject constructor(
    private val service: Api,
    private val userDao: UserDao
) : RxRemoteMediator<Int, UserDetails>() {

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, UserDetails>): Single<MediatorResult> {

        return when (loadType) {
            //determines whether we should use loadAndSaveUser or nots
            LoadType.REFRESH -> loadAndSaveUser()
            LoadType.PREPEND -> Single.just(MediatorResult.Success(endOfPaginationReached = true))
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                return if (lastItem == null) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = false))
                } else {
                    loadAndSaveUser(lastItem.user.id)
                }
            }

        }
    }

    //save users in db after loading them from webservice
    private fun loadAndSaveUser(since: Int = STARTING_PAGE_INDEX): Single<MediatorResult> {
        return service.getUsers(since)
            .flatMap {
                Completable.fromAction {
                    //save users in DB
                    userDao.insertMultipleUser(it)
                }.toSingleDefault(it)
            }
            .map<MediatorResult> {
                MediatorResult.Success(endOfPaginationReached = it.isEmpty())
            }
            .onErrorReturn {
                MediatorResult.Error(it)
            }
    }


    companion object {
        private const val TAG = "RxRemoteMediator"
        private const val STARTING_PAGE_INDEX = 0
    }
}