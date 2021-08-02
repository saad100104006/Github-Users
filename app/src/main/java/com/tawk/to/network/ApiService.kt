package com.tawk.to.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
    ): Single<Response<JsonArray>>


    @GET("users/{userName}")
    fun getUserDetails(
        @Path("userName") userName: String,
    ): Single<Response<JsonObject>>
}