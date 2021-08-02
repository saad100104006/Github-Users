package com.tawk.to.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val networkStatus = MutableLiveData<ConnectivityStatus>()

    fun getNetworkStatus(): LiveData<ConnectivityStatus> = networkStatus

    fun updateNetworkStatus(status: ConnectivityStatus) {
        networkStatus.postValue(status)
    }

}


sealed class ConnectivityStatus
object Connected : ConnectivityStatus()
object Disconnected : ConnectivityStatus()
