package com.cj.util

import android.content.Context
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    /*
     * 将时间转换为时间戳
     */
    @Throws(ParseException::class)
    fun dateToStamp(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = simpleDateFormat.parse(s)
        val ts = date.time
        res = ts.toString()
        return res
    }

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("MM-dd-HH:mm")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }

    fun formatDateYMDHM(milliseconds: Long): String {
        return formatDate("yyyy-MM-dd HH:mm", milliseconds)
    }

    fun formatDate(template: String, milliseconds: Long): String {
        return formatDate(template, Date(milliseconds))
    }

    fun formatDate(template: String, date: Date): String {
        val format = SimpleDateFormat(template, Locale.getDefault())
        return format.format(date)
    }

    fun formatAgoDate(context: Context, dateStr: String): String {
        //		SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		Date date1 = format1.parse(dateStr);
        //        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //        try {
        //            Date date = format.parse(dateStr);
        //            return FuzzyDateTimeFormatter.getTimeAgo(context, date, "yyyy-MM-dd");
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            return dateStr;
        //        }

        return formatMonthDayAgoDate(context, dateStr)
    }


    fun formatMonthDayAgoDate(context: Context, dateStr: String): String {
        //		SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		Date date1 = format1.parse(dateStr);
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val date = format.parse(dateStr)
            return FuzzyDateTimeFormatter.getTimeAgo(context, date, "MM-dd")
        } catch (e: Exception) {
            e.printStackTrace()
            return dateStr
        }

    }


    fun monthAndDay(context: Context, dateStr: String): String {
        val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val afterFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        try {
            val date = beforeFormat.parse(dateStr)
            return afterFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return dateStr
        }

    }

    fun formatParse(context: Context, before: String, after: String, time: String): String {
        val beforeFormat = SimpleDateFormat(before, Locale.getDefault())
        val afterFormat = SimpleDateFormat(after, Locale.getDefault())
        try {
            val date = beforeFormat.parse(time)
            return afterFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return time
        }

    }

    fun monthAndDay(context: Context, dateStr: String, format: String): String {
        val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val afterFormat = SimpleDateFormat(format, Locale.getDefault())
        try {
            val date = beforeFormat.parse(dateStr)
            return afterFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return dateStr
        }

    }

    fun formatAgoDateFullTime(context: Context, dateStr: String): String {
        //		SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		Date date1 = format1.parse(dateStr);
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val date = format.parse(dateStr)
            return FuzzyDateTimeFormatter.getTimeAgo(context, date, "yyyy-MM-dd HH:mm:ss")
        } catch (e: Exception) {
            e.printStackTrace()
            return dateStr
        }

    }

    fun formatAgoDateOneDay(context: Context, dateStr: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val date = format.parse(dateStr)
            return FuzzyDateTimeFormatter.getTimeAgoOneday(context, date)
        } catch (e: Exception) {
            e.printStackTrace()
            return dateStr
        }

    }

    fun formatUpdateApkFileNameDate(temp: Long?): String {
        return formatDate("yyyy-MM-dd HH:mm:ss", Date(temp!!))
    }

    fun formatUpdateApkFileNameDate(): String {
        return formatDate("yyyy-MM-dd HH:mm:ss", Date(System.currentTimeMillis()))
    }

    /**
     * 获取10分钟以内的毫秒数
     */
    fun formatMoJinDate(time1: String, time2: String): Long {
        if (TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)) {
            return -1
        }
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            return format.parse(time1).time - format.parse(time2).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return -1
    }

    /**
     * 把10分钟以内的毫秒数转为mm:ss
     */
    fun formatMoJinTime(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24

        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss

        val strMinute = if (minute < 10) "0$minute" else "" + minute// 分钟
        val strSecond = if (second < 10) "0$second" else "" + second// 秒

        return "$strMinute:$strSecond"
    }


    /**
     * 判断是否是同一天
     *
     * @return
     */
    fun isSameDayDates(datel: Long?): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentTime = Date(System.currentTimeMillis())
        //        Date spTime = new Date(PreferencesConfig.NOTCERFITYTIME.get());
        val spTime = Date(datel!!)
        val current = sdf.format(currentTime)
        val sp = sdf.format(spTime)

        return if (current == sp) {
            true
        } else {
            false
        }
    }


}
