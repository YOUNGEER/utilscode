package com.cj.util

import android.text.TextUtils

/**
 *@package:com.example.library.utils
 *@data on:2019/3/12 15:34
 *author:YOUNG
 *desc:TODO
 */
object DataCheckUtils {

    /**
     * 检查手机号是否合法
     */

    fun checkPhoneNum(phone: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(R.string.login_phone_empty)
            return false
        }
        if (!MatchUtils.isMobilePhone(phone)) {
            ToastUtils.showToast(R.string.login_phone_error)
            return false
        }

        return true
    }


    /**
     * 检查手机号和验证码
     */

    fun checkVerifyCode(phone: String, code: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(R.string.login_phone_empty)
            return false
        }
        if (!MatchUtils.isMobilePhone(phone)) {
            ToastUtils.showToast(R.string.login_phone_error)
            return false
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast(R.string.login_code_empty)
            return false
        }
        return true
    }


}