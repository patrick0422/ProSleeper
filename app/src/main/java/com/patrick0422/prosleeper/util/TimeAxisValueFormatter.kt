package com.patrick0422.prosleeper.util

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeAxisValueFormatter: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return LocalTime.ofSecondOfDay(value.toLong() * 60L).format(DateTimeFormatter.ofPattern("hh:mm"))
    }
}