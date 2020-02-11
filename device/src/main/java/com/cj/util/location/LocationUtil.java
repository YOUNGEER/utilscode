package com.cj.util.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.PermissionChecker;

import java.util.List;

public class LocationUtil {

    private static LocationUtil sInstance;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    private Context mContext;
    private LocationManager mLocationManager;

    private LocationUtil(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocationUtil(context);
        }
        return sInstance;
    }

    /**
     * 单次定位
     *
     * @param locationListener
     */

    @SuppressLint("MissingPermission")
    public void requestSingleLocation(@NonNull OnLocationResultListener locationListener) {
        mOnLocationListener = locationListener;
//        if (lacksPermissions(PERMISSIONS)) {
//            if (mOnLocationListener != null) {
//                mOnLocationListener.onLocationFailed();
//            }
//        }
        mLocationManager.requestSingleUpdate(getProvider(), this.locationListener, null);
    }

    /**
     * 持续定位
     *
     * @param interval         间隔时长，以毫秒为单位
     * @param locationListener
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    public void requestIntervalLocation(long interval, @NonNull OnLocationResultListener locationListener) {
        mOnLocationListener = locationListener;
        if (lacksPermissions(PERMISSIONS)) {
            if (mOnLocationListener != null) {
                mOnLocationListener.onLocationFailed();
            }
        }
        mLocationManager.requestLocationUpdates(getProvider(), interval, 1, this.locationListener);
    }

    @SuppressLint("MissingPermission")
    public Location getLastKnowLocation() {
        return mLocationManager.getLastKnownLocation(getProvider());
    }

    @SuppressLint("MissingPermission")
    public String getLastKnowLocationStr() {
        Location location = mLocationManager.getLastKnownLocation(getProvider());
        if (location != null) {
            return String.format("%.6f,%.6f", location.getLongitude(), location.getLatitude());
        }
        return null;
    }

    private String getProvider() {
        // 获取所有可用的位置提供器
        List<String> providers = mLocationManager.getProviders(true);
        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) { // GPS
            Log.i("dddddddddd", "GPS");
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) { // Network
            Log.i("dddddddddd", "Network");
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.i("dddddddddd", "else");
//            Intent i = new Intent();
//            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            mContext.startActivity(i);
            if (mOnLocationListener != null) {
                mOnLocationListener.onLocationFailed();
            }
        }
        return locationProvider;
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            location.dump(new LogPrinter(Log.DEBUG, "gps-dump"), "");
            if (mOnLocationListener != null) {
                mOnLocationListener.onLocationResult(location);
            }
        }
    };

    public void removeListener() {
        mLocationManager.removeUpdates(locationListener);
    }

    private OnLocationResultListener mOnLocationListener;

    public interface OnLocationResultListener {

        void onLocationResult(Location location);

        void onLocationFailed();
    }

    private boolean lacksPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
