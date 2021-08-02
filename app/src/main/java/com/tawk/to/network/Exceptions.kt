package com.tawk.to.network

class NoNetworkException(message: String = "No internet connection available. Please connect with internet and try again") :
    Exception(message)
