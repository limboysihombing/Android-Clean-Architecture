package com.app.habittracker.utils.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast

@Suppress("DEPRECATION")
fun Context.isNetworkAvailable() : Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
//Internet connectivity check in Android Q
    val networks = cm.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val networkCapabilities = cm.getNetworkCapabilities(network)
            if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) hasInternet = true
        }
    }
    return hasInternet
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}
