package com.wei.wanandroidkotlin.util

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings

/**
 *
 * @author XiangWei
 * @since 2019/1/8
 */
class LocationUtil {

    private val context: Context? = null
    private var locationManager: LocationManager? = null
    var onLocationResultListener: ((location: Location) -> Unit)? = null

    fun getLngAndLat(onLocationResultListener: ((location: Location) -> Unit)): Unit? {
        context?.let { context ->
            var locationProvider: String?
            this.onLocationResultListener = onLocationResultListener
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager?.let { locMng ->
                val provides = locMng.getProviders(true)
                when {
                    provides.contains(LocationManager.GPS_PROVIDER) -> // GPS 定位
                        locationProvider = LocationManager.GPS_PROVIDER
                    provides.contains(LocationManager.NETWORK_PROVIDER) -> // 网络定位
                        locationProvider = LocationManager.NETWORK_PROVIDER
                    else -> {
                        // 跳转到设置界面，打开定位
                        val intent = Intent()
                        intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        context.startActivity(intent)
                        return null
                    }
                }
                val location = locMng.getLastKnownLocation(locationProvider)
                location?.let {
                    onLocationResultListener.invoke(location)
                }
                locMng.requestLocationUpdates(locationProvider, 3000, 1f, locationListener)
            }
        }
        return null
    }

    private fun shouldCheckPermissions(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            location?.let { onLocationResultListener?.invoke(it) }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    }

}
