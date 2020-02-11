package com.cj.util.info

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils

import com.cj.util.Utils

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_PHONE_STATE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.net.wifi.WifiManager
import com.cj.util.PackagedApp
import com.cl.library.utils.AppUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about phone
</pre> *
 */
object PhoneUtils {

    /**
     * 获取已安装的app
     */
    fun appList(context: Context): List<PackagedApp> {
        var applist = arrayListOf<PackagedApp>()
        val manager = context.packageManager as PackageManager
        //获取手机内所有应用
        val apkList = manager.getInstalledPackages(0) as List<PackageInfo>
        apkList.forEach {
            if ((it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) <= 0) {

                val appName = it.applicationInfo.loadLabel(manager)
                    .toString()
                val packageName = it.applicationInfo.packageName
                val info = PackagedApp(appName, packageName);
                applist.add(info)
            }
        }
        return applist;
    }


    /**
     * 获取设备ip
     */
    fun ipAddr(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        val ip =
            (ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "." + (ipAddress shr 16 and 0xff) + "." + (ipAddress shr 24 and 0xff)
        return ip
    }

    /**
     * 判断是否是模拟器
     */
    fun isEmulator(mContext: Context): Boolean {
        val url = "tel:" + "123456";
        val intent = Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        val canResolveIntent: Boolean = intent.resolveActivity(mContext.getPackageManager()) != null

        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL == "unknown"
                || Build.SERIAL == "android"
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).getNetworkOperatorName().toLowerCase() == "android")
                || !canResolveIntent)
    }

    /**
     * mac 地址
     */
    fun wmac(context: Context): String? {
        var macAddress: String? = ""
        val wifi = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        macAddress = info.macAddress
        if (null == macAddress) {
            return ""
        }
        macAddress = macAddress.replace(":", "")
        return macAddress
    }

    /**
     * 获取手机厂商，如Xiaomi
     */
    fun brand(): String? {
        return Build.BRAND
    }


    /**
     * 获取手机型号，如MI2SC
     */
    fun model(): String? {
        return Build.MODEL
    }

    /**
     * 系统版本 比如Android 9
     */
    fun os(): String? {
        return Build.VERSION.RELEASE;
    }


    /**
     * Return the unique device id.
     *
     * If the version of SDK is greater than 28, it will return an empty string.
     *
     * Must hold `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @return the unique device id
     */
    val deviceId: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            if (Build.VERSION.SDK_INT >= 29) {
                return ""
            }
            val tm = telephonyManager
            val deviceId = tm.deviceId
            if (!TextUtils.isEmpty(deviceId)) return deviceId
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val imei = tm.imei
                if (!TextUtils.isEmpty(imei)) return imei
                val meid = tm.meid
                return if (TextUtils.isEmpty(meid)) "" else meid
            }
            return ""
        }

    /**
     * Return the serial of device.
     *
     * @return the serial of device
     */
    val serial: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Build.getSerial() else Build.SERIAL


    /**
     * 需要state权限
     * Android 10 获取不到，target<=28即可
     */
    val imei: String?
        get() = getImeiOrMeid(true)


    /**
     * 需要state权限
     * Android 10 获取不到，target<=28即可
     */
    val meid: String?
        get() = getImeiOrMeid(false)

    @SuppressLint("HardwareIds", "MissingPermission")
    fun getImeiOrMeid(isImei: Boolean): String? {
        if (Build.VERSION.SDK_INT >= 29) {
            return ""
        }
        val tm = telephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return if (isImei) {
                getMinOne(tm.getImei(0), tm.getImei(1))
            } else {
                getMinOne(tm.getMeid(0), tm.getMeid(1))
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val ids =
                getSystemPropertyByReflect(if (isImei) "ril.gsm.imei" else "ril.cdma.meid")
            if (!TextUtils.isEmpty(ids)) {
                val idArr =
                    ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return if (idArr.size == 2) {
                    getMinOne(idArr[0], idArr[1])
                } else {
                    idArr[0]
                }
            }

            var id0: String? = tm.deviceId
            var id1: String? = ""
            try {
                val method =
                    tm.javaClass.getMethod("getDeviceId", Int::class.javaPrimitiveType!!)
                id1 = method.invoke(
                    tm,
                    if (isImei)
                        TelephonyManager.PHONE_TYPE_GSM
                    else
                        TelephonyManager.PHONE_TYPE_CDMA
                ) as String
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

            if (isImei) {
                if (id0 != null && id0.length < 15) {
                    id0 = ""
                }
                if (id1 != null && id1.length < 15) {
                    id1 = ""
                }
            } else {
                if (id0 != null && id0.length == 14) {
                    id0 = ""
                }
                if (id1 != null && id1.length == 14) {
                    id1 = ""
                }
            }
            return getMinOne(id0, id1)
        } else {
            val deviceId = tm.deviceId
            if (isImei) {
                if (deviceId != null && deviceId.length >= 15) {
                    return deviceId
                }
            } else {
                if (deviceId != null && deviceId.length == 14) {
                    return deviceId
                }
            }
        }
        return ""
    }

    private fun getMinOne(s0: String?, s1: String?): String? {
        val empty0 = TextUtils.isEmpty(s0)
        val empty1 = TextUtils.isEmpty(s1)
        if (empty0 && empty1) return ""
        if (!empty0 && !empty1) {
            return if (s0!!.compareTo(s1!!) <= 0) {
                s0
            } else {
                s1
            }
        }
        return if (!empty0) s0 else s1
    }

    private fun getSystemPropertyByReflect(key: String): String {
        try {
            @SuppressLint("PrivateApi")
            val clz = Class.forName("android.os.SystemProperties")
            val getMethod = clz.getMethod("get", String::class.java, String::class.java)
            return getMethod.invoke(clz, key, "") as String
        } catch (e: Exception) {/**/
        }

        return ""
    }

    /**
     * Return the IMSI.
     *
     * Must hold `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @return the IMSI
     */
    val imsi: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() = telephonyManager.subscriberId

    /**
     * Return whether sim card state is ready.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    val isSimCardReady: Boolean
        get() {
            val tm = telephonyManager
            return tm.simState == TelephonyManager.SIM_STATE_READY
        }

    /**
     * Return the sim operator name.
     *
     * @return the sim operator name
     */
    val simOperatorName: String
        get() {
            val tm = telephonyManager
            return tm.simOperatorName
        }



     val telephonyManager: TelephonyManager
        get() = AppUtils.context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


}
