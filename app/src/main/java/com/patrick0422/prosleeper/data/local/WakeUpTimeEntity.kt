package com.patrick0422.prosleeper.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
class WakeUpTimeEntity(
    var wakeUpTime: LocalDateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}