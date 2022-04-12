package com.patrick0422.prosleeper.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WakeUpTimeTypeConverter {
    private val localDateTimePattern = "yyyy-MM-dd HH:mm:ss"

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String =
        localDateTime.format(DateTimeFormatter.ofPattern(localDateTimePattern))

    @TypeConverter
    fun stringToLocalDateTime(string: String): LocalDateTime =
        LocalDateTime.parse(string, DateTimeFormatter.ofPattern(localDateTimePattern))
}