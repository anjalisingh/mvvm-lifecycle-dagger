package com.solutions.mvvmlifeycledagger.utils

/**
 * Created by anjalisingh on 14,January,2019
 */

class MiscUtils {

    companion object {
        fun getTime(unixSeconds : Long?, secs : Boolean = false) : String?{
            unixSeconds?.let {
                // convert seconds to milliseconds
                val date = java.util.Date(unixSeconds * 1000L)
                // the format of your date

                var sdf = java.text.SimpleDateFormat("MMMM dd")
                // give a timezone reference for formatting (see comment at the bottom)

                if(secs) {
                    sdf = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm a")
                }
                sdf.timeZone = java.util.TimeZone.getDefault()
                val formattedDate = sdf.format(date)
                return formattedDate
            }

            return "-"
        }
    }



}