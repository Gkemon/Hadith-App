package com.gk.emon.core_features.base_ui_containers

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gk.emon.core_features.network.NetworkHandler

abstract class BaseActivity : AppCompatActivity(), NetworkHandler.NetworkConnectivityListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkHandler.ConnectionLiveData(this).observe(this, {
            val networkConnectionEvent = it.getContentIfNotHandled()
            networkConnectionEvent?.let {
                when (networkConnectionEvent) {
                    is NetworkHandler.NetworkEvent.NetworkConnected -> {
                        onNetworkConnected(networkConnectionEvent)
                    }
                    is NetworkHandler.NetworkEvent.NetworkDisconnected -> {
                        onNetworkDisconnected(networkConnectionEvent)
                    }
                    NetworkHandler.NetworkEvent.NetworkUnavailable -> {
                        onNetworkDisconnected(networkConnectionEvent)
                    }
                }
            }
        })
    }

    override fun onNetworkConnected(networkEvent: NetworkHandler.NetworkEvent) {

    }

    override fun onNetworkDisconnected(networkEvent: NetworkHandler.NetworkEvent) {

    }


    abstract fun getViewContainer(): View

}