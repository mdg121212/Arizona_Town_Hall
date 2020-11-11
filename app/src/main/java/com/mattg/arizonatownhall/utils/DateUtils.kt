package com.mattg.arizonatownhall.utils




open class DateUtils {
    companion object{
        @JvmStatic
        fun formatTimeForDataBind(start: String, end: String): String {
            //manually splitting the input strings, was not liking built in methods
            var startTime = start.substring(11, 16)
            var startTimeHours = startTime.substring(0,2).toInt()
            var endTime = end.substring(11, 16)
            var endTimeHours = endTime.substring(0,2).toInt()
            val startAmPm: String
            val endAmPm: String
            val output: String
            when {
                startTimeHours > 12 -> {
                    startTimeHours -=12
                    startAmPm = "PM"
                    startTime = "$startTimeHours:${startTime.substring(3,5)}"
                }
                startTimeHours == 12 -> {
                    startAmPm = "PM"
                }
                else -> {
                    startAmPm = "AM"
                }
            }
            when {
                endTimeHours > 12 -> {
                    endTimeHours -= 12
                    endAmPm = "PM"
                    endTime = "$endTimeHours:${endTime.substring(3,5)}"
                }
                endTimeHours == 12 -> {
                    endAmPm = "PM"
                }
                else -> {
                    endAmPm = "AM"
                }
            }


            val startDate = start.substring(0, 10)
            val endDate = end.substring(0, 10)
            val startToBack = startDate.substring(0,4)
            val startToFront = startDate.substring(5,10)
            val endToBack = endDate.substring(0,4)
            val endToFront = endDate.substring(5,10)
            val formattedStartDate = "$startToFront-$startToBack"
            val formattedEndDate = "$endToFront-$endToBack"

            output = "Start Time/Date: $startTime$startAmPm - $formattedStartDate\nEnd Time/Date: $endTime$endAmPm - $formattedEndDate"

            return output
        }
    }
}