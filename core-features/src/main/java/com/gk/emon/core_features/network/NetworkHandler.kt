package com.gk.emon.core_features.network

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.lifecycle.LiveData
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.extensions.connectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(@ApplicationContext private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.connectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    sealed class NetworkEvent(open var network: Network? = null) {
        object NetworkConnected : NetworkEvent()
        object NetworkDisconnected : NetworkEvent()
        object NetworkUnavailable : NetworkEvent()
    }

    interface NetworkConnectivityListener {
        fun onNetworkConnected(networkEvent: NetworkEvent)
        fun onNetworkDisconnected(networkEvent: NetworkEvent)
    }

    class ConnectionLiveData(private val context: Context) : LiveData<Event<NetworkEvent>>() {
        private val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        private var networkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                postValue((Event(NetworkEvent.NetworkConnected.apply {
                    this.network = network
                })))
            }

            override fun onUnavailable() {
                postValue((Event(NetworkEvent.NetworkUnavailable)))
            }

            override fun onLost(network: Network) {
                postValue((Event(NetworkEvent.NetworkDisconnected.apply {
                    this.network = network
                })))
            }
        }

        override fun onActive() {
            super.onActive()

            if (!NetworkHandler(context).isNetworkAvailable())
                postValue((Event(NetworkEvent.NetworkUnavailable)))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cm.registerDefaultNetworkCallback(networkCallback)
            } else {
                val networkRequest = NetworkRequest.Builder().build()
                cm.registerNetworkCallback(networkRequest, networkCallback)
            }
        }

        override fun onInactive() {
            super.onInactive()
            cm.unregisterNetworkCallback(networkCallback)
        }
    }
}
