package com.cj.sn

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.webkit.PermissionRequest
import com.cj.util.location.LocationUtil

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("dddddddddd", "00000")

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                11
            )
        } else {

            Log.i("dddddddddd", "11111")
            location()
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 11) {

            Log.i("dddddddddd", "22222")
            location()

        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun location() {
        val loca = LocationUtil.getInstance(this)

        val last = loca.lastKnowLocation


        Log.i("dddddddddd", "last::=>" + last)

        loca.requestSingleLocation(object : LocationUtil.OnLocationResultListener {
            override fun onLocationFailed() {

                Log.i("dddddddddd", "failer")
            }

            override fun onLocationResult(location: Location?) {

                Log.i("dddddddddd", "network"+location.toString())
            }
        })
    }


}
