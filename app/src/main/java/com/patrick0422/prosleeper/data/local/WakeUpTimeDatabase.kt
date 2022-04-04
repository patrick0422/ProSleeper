package com.patrick0422.prosleeper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [WakeUpTimeEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(WakeUpTimeTypeConverter::class)
abstract class WakeUpTimeDatabase : RoomDatabase() {
    abstract fun wakeUpTimeDao(): WakeUpTimeDao
}