package com.gk.emon.core_features.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData
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
        object NetworkStateUnknown : NetworkEvent()

    }

    fun networkConnectivityEventListener(): MutableLiveData<Event<NetworkEvent>> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        val networkEventsLiveData =
            MutableLiveData<Event<NetworkEvent>>(Event(NetworkEvent.NetworkStateUnknown))
        cm.registerNetworkCallback(
            builder.build(),
            object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    networkEventsLiveData.postValue(Event(NetworkEvent.NetworkConnected.apply {
                        this.network = network
                    }))
                }

                override fun onLost(network: Network) {
                    networkEventsLiveData.postValue(Event(NetworkEvent.NetworkDisconnected.apply {
                        this.network = network
                    }))
                }
            }
        )
        return networkEventsLiveData
    }

}
