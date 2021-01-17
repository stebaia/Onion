package com.sbaiardi.onion.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtils {

    companion object{
         fun dateFormat(dateToFormat: String): String{
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = SimpleDateFormat("dd-MM-yyyy")
            val date: Date = inputFormat.parse(dateToFormat)
            return outputFormat.format(date)
        }

    }

}