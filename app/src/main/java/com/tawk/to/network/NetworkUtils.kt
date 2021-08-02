package com.tawk.to.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class NetworkUtils @Inject constructor(@ApplicationContext private val context: Context) {
    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)

            mCapabilities != null && (mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || mCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ))
        } else {
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val status = activeNetwork?.isConnectedOrConnecting == true
            Log.d("NetworkUtils", "status: $status")
            status
        }

    }

    fun isConnectedSingle(): Single<Boolean> {
        return Single.just(isConnected())
    }
}