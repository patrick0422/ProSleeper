package com.patrick0422.prosleeper.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WakeUpTimeDao {
    @Query("SELECT * FROM WakeUpTimeEntity ORDER BY id ASC;")
    fun getWakeUpTimes(): Flow<List<WakeUpTimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity)

    @Delete
    suspend fun deleteWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity)
}