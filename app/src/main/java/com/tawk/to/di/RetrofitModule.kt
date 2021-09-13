package com.tawk.to.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.tawk.to.network.ApiService
import com.tawk.to.utils.Constants.Companion.API_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://api.github.com/"

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(@ApplicationContext context: Context): OkHttpClient.Builder {
        // Network calls limited to 1 call at a time
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "token $API_TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    }


    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}

