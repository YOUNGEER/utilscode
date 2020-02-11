package com.cj.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import com.cl.library.utils.AppUtils

/**
 * 为了防止包级函数和类函数命名重复，
 * 建议包级函数名字F结尾
 */

/**
 * app的版本号
 */
fun verCodeF(): Int {
    var verCode = -1
    try {
        val packageName = AppUtils.context.packageName
        verCode = AppUtils.context.packageManager
            .getPackageInfo(packageName, 0).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return verCode
}


/**
 * app的版本名
 */
fun verNameF(): String {
    var verName = ""
    try {
        val packageName = AppUtils.context.packageName
        verName = AppUtils.context.packageManager
            .getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return verName
}


/**
 * 获取屏幕的宽度
 */
fun screenWidthF(): Int {
    val wm = AppUtils.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获取屏幕的高度
 */
fun screenHeightF(): Int {
    val wm = AppUtils.context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

fun displayMetricsF() = AppUtils.context.resources.displayMetrics

/**
 * sp转px
 */
fun sp2pxF(spValue: Float): Float = spValue * displayMetricsF().scaledDensity + 0.5f

/**
 * dp转px
 */
fun dp2pxF(dpValue: Float) = dpValue * displayMetricsF().density


/**
 * 获取状态栏的高度
 */
fun statusBarHeightF(): Int {
    var statusHegiht = 24
    val resourceId = AppUtils.context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusHegiht = AppUtils.context.resources.getDimensionPixelSize(resourceId)
    } else {
        statusHegiht = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            statusHegiht.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()
    }
    return statusHegiht
}


































