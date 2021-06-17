package com.gk.emon.core_features.base_framework_ui

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.network.NetworkHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    private var networkCollectionEventLiveData =
        MutableLiveData<Event<NetworkHandler.NetworkEvent>>()

    @Inject
    lateinit var networkHandler: NetworkHandler
    override fun onStart() {
        super.onStart()
        networkCollectionEventLiveData = networkHandler.networkConnectivityEventListener()
        networkCollectionEventLiveData.observe(this, {
            when (it.peekContent()) {
                is NetworkHandler.NetworkEvent.NetworkConnected -> {
                    Toast.makeText(this, "Internet connected", Toast.LENGTH_SHORT).show()
                }
                is NetworkHandler.NetworkEvent.NetworkDisconnected -> {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }

    abstract fun getViewContainer(): View
}