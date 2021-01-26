package com.mattg.aztownhall.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.mattg.aztownhall.BuildConfig

object Constants {

    const val apiKey = BuildConfig.API_KEY
    const val apiClientId = BuildConfig.API_CLIENT_ID
    const val apiClientSecret = BuildConfig.API_CLIENT_SECRET
    const val twitterApiKey = BuildConfig.TWITTER_API_KEY
    const val twitterSecret = BuildConfig.TWITTER_SECRET
    const val twitterBearer = BuildConfig.TWITTER_BEARER
    val accountIdAdmin = BuildConfig.ACCOUNT_ID_ADMIN
    var refreshToken = ""
    var token = ""


    fun isNetworkAvailable(context: Context): Boolean {
        // It answers the queries about the state of network connectivity.
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network      = connectivityManager.activeNetwork ?: return false
            val activeNetWork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other devices that are able to connect with Ethernet
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            // Returns details about the currently active default data network.
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }
}