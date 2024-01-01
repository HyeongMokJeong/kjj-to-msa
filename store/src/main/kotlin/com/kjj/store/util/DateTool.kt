package com.kjj.store.util

import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

@Component
class DateTool {
    companion object {
        fun makeLocalDateToFormatterString(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            return date.format(formatter)
        }
    }
    fun makeTodayToLocalDate(): LocalDate {
        return LocalDate.now()
    }

    fun makeLocalDate(year: Int, month: Int, day: Int): LocalDate {
        return LocalDate.of(year, month, day)
    }

    fun getLastDay(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar[year, month - 1] = 1
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getLastWeeksMonday(type: Int): LocalDate {
        return LocalDateTime.now().minusWeeks((type + 1).toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate()
    }
}