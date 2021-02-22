package com.cj.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object FuzzyDateTimeFormatter {

    private val SECONDS = 1
    private val MINUTES = 60 * SECONDS
    private val HOURS = 60 * MINUTES
    private val DAYS = 24 * HOURS
    private val WEEKS = 7 * DAYS
    private val MONTHS = 4 * WEEKS
    private val YEARS = 12 * MONTHS

    /**
     * 一天以内，返回多少分钟，小时之前，超过一天，就返回具体的时间
     *
     * @param context Context
     * @param date    Absolute date of the event
     * @return Formatted string
     */
    fun getTimeAgo(context: Context, date: Date, pattern: String): String {
        val beforeSeconds = (date.time / 1000).toInt()
        val nowSeconds = (Calendar.getInstance().timeInMillis / 1000).toInt()
        val timeDifference = nowSeconds - beforeSeconds

        val res = context.resources
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        val yearFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return if (timeDifference < MINUTES) {
            if (timeDifference < 10) {
                res.getString(R.string.date_ago_text_1)
            } else {
                res.getString(R.string.date_ago_text_2, timeDifference)
            }
        } else if (timeDifference < HOURS) {
            res.getString(R.string.date_ago_text_3, timeDifference / MINUTES)
        } else if (timeDifference < DAYS) {
            res.getString(R.string.date_ago_text_4, timeDifference / HOURS)
        } else {
            try {
                val rightNow = Calendar.getInstance()
                val str = yearFormat.format(date)
                if (str.substring(0, 4) == rightNow.get(Calendar.YEAR).toString() + "") {
                    format.format(date)
                } else {
                    yearFormat.format(date)
                }
            } catch (e: Exception) {
                format.format(date)
            }

        }
    }


    /**
     * 一天以内，返回多少分钟，小时之前，超过一天，就返回具体的时间
     *
     * @param context Context
     * @param date    Absolute date of the event
     * @return Formatted string
     */
    fun getTimeAgoOneday(context: Context, date: Date): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return if (isNow(date)) {
            "${context.getString(R.string.date_ago_text_0)}   ${format.format(date)}"
//            TextUtils.appendText(context.getString(R.string.date_ago_text_0), "  ", format.format(date))
        } else {
            format2.format(date)
        }
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private fun isNow(date: Date): Boolean {
        //当前时间
        val now = Date()
        val sf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //获取今天的日期
        val nowDay = sf.format(now)
        //对比的时间
        val day = sf.format(date)
        return day == nowDay

    }

    /**
     * Returns a properly formatted fuzzy string representing time ago
     *
     * @param context Context
     * @param date    Absolute date of the event
     * @return Formatted string
     */
    fun getTimeAgoWechat(context: Context, date: Date): String {
        val beforeSeconds = (date.time / 1000).toInt()
        val nowSeconds = (Calendar.getInstance().timeInMillis / 1000).toInt()
        val timeDifference = nowSeconds - beforeSeconds

        val res = context.resources

        return if (timeDifference < MINUTES) {
            if (timeDifference < 10) {
                res.getString(R.string.date_ago_text_1)
            } else {
                res.getString(R.string.date_ago_text_2, timeDifference)
            }
        } else if (timeDifference < HOURS) {
            res.getString(R.string.date_ago_text_3, timeDifference / MINUTES)
        } else if (timeDifference < DAYS) {
            res.getString(R.string.date_ago_text_4, timeDifference / HOURS)
        } else if (timeDifference < WEEKS) {
            res.getString(R.string.date_ago_text_5, timeDifference / DAYS)
        } else if (timeDifference < MONTHS) {
            res.getString(R.string.date_ago_text_6, timeDifference / WEEKS)
        } else if (timeDifference < YEARS) {
            res.getString(R.string.date_ago_text_7, timeDifference / MONTHS)
        } else {
            res.getString(R.string.date_ago_text_8, timeDifference / YEARS)
        }

    }
}
