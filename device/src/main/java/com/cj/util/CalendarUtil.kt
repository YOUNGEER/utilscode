package com.cj.util

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {

    /**
     * 获取当前时间
     * 按照format的格式
     */
    fun getCurrentTime(format: String = "yyyy-MM-dd"): String {
        val now: Calendar = Calendar.getInstance()
        val d = now.time
        //"yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        return sdf.format(d)
    }

    fun getAfterDay(amount: Int = 100, format: String = "yyyy-MM-dd"): String {
        val sdf = SimpleDateFormat(format)
        val after7 = Calendar.getInstance()
        after7.add(Calendar.DAY_OF_MONTH, +amount)
        return sdf.format(after7.time)
    }

    fun getBeforeDay(amount: Int = 100, format: String = "yyyy-MM-dd"): String {
        val sdf = SimpleDateFormat(format)
        val after7 = Calendar.getInstance()
        after7.add(Calendar.DAY_OF_MONTH, -amount)
        return sdf.format(after7.time)
    }

    fun compareDaysTime(
        date1: String?,
        date2: String?,
        format: String = "yyyy-MM-dd HH:mm:ss"
    ): Int {
        val df = SimpleDateFormat(format)
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        try {
            calendar1.time = df.parse(date1)
            calendar2.time = df.parse(date2)
        } catch (e: Exception) {
            return 0
        }


        var h1 = calendar1[Calendar.HOUR_OF_DAY]
        var h2 = calendar2[Calendar.HOUR_OF_DAY]

        var m1 = calendar1[Calendar.MINUTE]
        var m2 = calendar2[Calendar.MINUTE]

        var s1 = calendar1[Calendar.SECOND]
        var s2 = calendar2[Calendar.SECOND]

        var totalS1 = h1 * 3600 + m1 * 60 + s1
        var totalS2 = h2 * 3600 + m2 * 60 + s1

        return totalS2 - totalS1;
    }

    /**
     * date2-date1
     * =0表示时间相同
     * >0 表示后面的时间大于前面的时间
     * <0 表示后面的时间小于前面的时间
     */
    fun compareDays(date1: String?, date2: String?, format: String = "yyyy-MM-dd"): Int {
        val df = SimpleDateFormat(format)
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar1.time = df.parse(date1)
        calendar2.time = df.parse(date2)
        var day1 = calendar1[Calendar.DAY_OF_YEAR]
        var day2 = calendar2[Calendar.DAY_OF_YEAR]
        var year1 = calendar1[Calendar.YEAR]
        var year2 = calendar2[Calendar.YEAR]

        if (year1 > year2) {
            var DayCount = 0
            for (i in year2 until year1) {
                DayCount += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    366
                } else {
                    365
                }
            }
            return -DayCount + (day2 - day1)
        }
        if (year1 == year2) {
            return day2 - day1

        } else {
            var DayCount = 0
            for (i in year1 until year2) {
                DayCount += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    366
                } else {
                    365
                }
            }
            return DayCount + (day2 - day1)
        }
    }

    /**
     * 时间差对比
     */
    fun compareTimes(date: String?, format: String = "yyyy-MM-dd"): Long {
        val df = SimpleDateFormat(format)
        val currStmp = System.currentTimeMillis()
        val dateStmp = df.parse(date).time
        val delet = dateStmp - currStmp;
        return delet / 1000
    }

    fun stmpToHint(delet: Long): String {
        if (delet < 0) {
            return ""
        }
        if (delet < 60) {
            return "${delet}秒"
        } else if (delet < 3600) {
            val min = delet / 60
            val send = delet - (min * 60)
            return "${min}分${send}秒"
        } else if (delet < 3600 * 24) {
            val hour = delet / 3600
            val min = (delet - (hour * 3600)) / 60
            val send = delet - (hour * 3600) - (min * 60)
            return "${hour}时${min}分${send}秒"
        } else {
            val day = delet / (3600 * 24)
            val hour = (delet - (day * 3600 * 24)) / 3600
            val min = (delet - (day * 3600 * 24) - (hour * 3600)) / 60
            val send = delet - (day * 3600 * 24) - (hour * 3600) - (min * 60)
            return "${day}天${hour}时${min}分${send}秒"
        }
    }

    fun gongli(date: String?, format: String = "yyyy-MM-dd"): String {
        val df = SimpleDateFormat(format)
        val df2 = SimpleDateFormat("yyyy年MM月dd日 HH时mm分")

        val time = df.parse(date)
        return "${df2.format(time)}"
    }

    fun gongli2(date: String?, format: String = "yyyy-MM-dd"): String {
        val df = SimpleDateFormat(format)
        val df2 = SimpleDateFormat("yyyy年MM月dd日")

        val time = df.parse(date)
        return "${df2.format(time)}"
    }

}