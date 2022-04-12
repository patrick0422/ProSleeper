package com.patrick0422.prosleeper.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WakeUpTimeTypeConverter {
    companion object {
        val DATETIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String =
        localDateTime.format(DATETIME_FORMAT)

    @TypeConverter
    fun stringToLocalDateTime(string: String): LocalDateTime =
        LocalDateTime.parse(string, DATETIME_FORMAT)
}