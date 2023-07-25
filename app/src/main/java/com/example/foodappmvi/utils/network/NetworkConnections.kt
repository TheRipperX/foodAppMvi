package com.example.foodappmvi.utils.network

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnections @Inject constructor(private val nm: ConnectivityManager, private val nr: NetworkRequest): NetworkCheck {

    @SuppressLint("ObsoleteSdkInt")
    override fun observeNet(): Flow<NetworkCheck.Status> {
        return callbackFlow {
            val callback = object: ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(NetworkCheck.Status.Available) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(NetworkCheck.Status.Unavailable) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(NetworkCheck.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(NetworkCheck.Status.Lost) }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                nm.registerDefaultNetworkCallback(callback)
            }else {
                nm.registerNetworkCallback(nr, callback)
            }
            awaitClose {
                nm.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}