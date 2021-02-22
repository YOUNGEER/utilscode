package com.cj.util

import java.math.BigDecimal

/**
 * 作者：young on 2017/8/24 15:18
 */

object MathUtils {

    fun removeEString(d: String?): String {
        if (null == d || d == "") {
            return "0.0"
        }
        val d1 = java.lang.Double.parseDouble(d)
        return removeE(d1)

    }

    //方法二： BigDecimal
    private fun removeE(d: Double): String {
        val d1 = BigDecimal(java.lang.Double.toString(d))
        val d2 = BigDecimal(Integer.toString(1))
        // 四舍五入,保留2位小数
        return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP).toString()
    }


    fun toTwoDecimal(d: Double?): String {
        val b = BigDecimal(d!!.toString() + "")
        val data = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        //        java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
        //        NF.setGroupingUsed(false);//去掉科学计数法显示
        return removeE(data)
    }


    fun toOneDecimal(d: Double?): String {
        val b = BigDecimal(d!!.toString() + "")
        val data = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        //        java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
        //        NF.setGroupingUsed(false);//去掉科学计数法显示
        return removeE(data)
    }

    fun toTwoDecimal(d: String): String {
        return if (d.contains(".")) {
            val b = BigDecimal(d)
            val f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            f1.toString() + ""
        } else {
            "$d.00"
        }

    }

    fun strToTwoString(str: String?): String {
        var str = str
        if (null == str || "" == str) {
            return "0.00"
        }

        try {
            if (str.contains(".")) {
                val totalString =
                    str.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                var lastString = totalString[1]
                if (lastString.length == 1) {
                    lastString = lastString + "0"
                }
                if (lastString.length >= 2) {
                    lastString = lastString.subSequence(0, 2) as String
                }
                str = totalString[0] + "." + lastString
            } else {
                str = "$str.00"
            }
            return str
        } catch (e: Exception) {

            return "0.00"
        }

    }


    /**
     * Description: 去除数字字符串后无用的0
     *
     * @author hujian
     * @param
     * @CreateDate 2018/12/27 15:03
     */
    fun removeInvalidZero(str: String?): String {
        var s = str.toString()
        if (s.indexOf(".") > 0) {
            //正则表达
            //去掉后面无用的零
            s = s.replace("0+?$".toRegex(), "")
            //如小数点后面全是零则去掉小数点
            s = s.replace("[.]$".toRegex(), "")
        }

        return s
    }

}
