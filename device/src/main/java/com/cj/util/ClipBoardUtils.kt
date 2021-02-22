package com.cj.util

import android.content.ClipData
import android.content.Context
import com.cl.library.utils.AppUtils

/**
 *@package:com.example.library.utils
 *@data on:2019/4/30 16:14
 *author:YOUNG
 *desc:TODO
 */
object ClipBoardUtils {
    fun clipboardCopyText(text: CharSequence) {
        val cm =
            AppUtils.context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val mClipData = ClipData.newPlainText("Label", text)
        cm.setPrimaryClip(mClipData)
        ToastUtils.showToast(R.string.copy_success)
    }


}