package com.kjj.user.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateUtil {
    companion object{
        fun makeLocalDate(year: Int, month: Int, day: Int): LocalDate {
            return LocalDate.of(year, month, day)
        }

        fun makeLocalDateToFormatterString(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            return date.format(formatter)
        }
    }
}