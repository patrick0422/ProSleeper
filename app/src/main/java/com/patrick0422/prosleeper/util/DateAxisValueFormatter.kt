package com.patrick0422.prosleeper.util

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateAxisValueFormatter: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val localDate = LocalDate.ofYearDay(2022, value.toInt())

        return localDate.format(DateTimeFormatter.ofPattern("MM-dd"))
    }
}