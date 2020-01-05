package com.rental_apps.android.rental_apps.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object DateDifference {
    var sdFormat = SimpleDateFormat("yyyy-MM-dd")
    @JvmStatic
    fun betweenDates(firstDate: String?, secondDate: String?): Long {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"))
        var daysDiff: Long = 0
        var timeDiff: Long = 0
        try {
            val startDateObj = sdFormat.parse(firstDate)
            val endDateObj = sdFormat.parse(secondDate)
            timeDiff = endDateObj.time - startDateObj.time
            daysDiff = timeDiff / (1000 * 60 * 60 * 24)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return daysDiff
    }
}