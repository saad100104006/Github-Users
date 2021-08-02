package com.tawk.to.network

import com.tawk.to.db.entity.Profile
import com.tawk.to.db.entity.User
import com.tawk.to.network.mapper.UserDetailsResponseMapper
import com.tawk.to.network.mapper.UsersResponseMapper
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

class Api
@Inject constructor(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils,
    private val usersResponseMapper: UsersResponseMapper,
    private val userDetailsResponseMapper: UserDetailsResponseMapper,
) {

    fun getUsers(since: Int): @NonNull Single<List<User>> {
        return networkUtils.isConnectedSingle()
            .flatMap {
                if (it == false) {
                    throw NoNetworkException()
                }

                apiService.getUsers(since)
            }
            .map {
                val body = it.body() ?: throw Exception()
                usersResponseMapper.modelFromEntity(body)
            }
    }

    fun getUserDetails(userName: String): @NonNull Single<Profile> {
        return networkUtils.isConnectedSingle()
            .flatMap {
                if (it == false) {
                    throw NoNetworkException()
                }

                apiService.getUserDetails(userName)
            }
            .map {
                val body = it.body() ?: throw Exception()
                userDetailsResponseMapper.modelFromEntity(body)
            }
    }

    companion object {
        private const val TAG = "Api"
    }
}