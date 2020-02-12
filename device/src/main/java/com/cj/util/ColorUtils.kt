package com.example.library.utils

import android.graphics.Color

/**
 * Created by Administrator on 2018/5/7.
 */

object ColorUtils {

    /**
     * 判断color是否为null或者是否符合条件
     *
     * @param color
     * @return
     */
    fun parseColor(color: String?): Int {
        if (null == color || "" == color) {
            return Color.BLUE
        }
        try {
            return Color.parseColor(color)
        } catch (e: Exception) {
            return Color.BLUE
        }

    }

}
