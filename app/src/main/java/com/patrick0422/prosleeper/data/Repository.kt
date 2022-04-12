package com.patrick0422.prosleeper.data

import com.patrick0422.prosleeper.data.local.WakeUpTimeDao
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val wakeUpTimeDao: WakeUpTimeDao
) {
    fun getWakeUpTimes(): Flow<List<WakeUpTimeEntity>> =
        wakeUpTimeDao.getWakeUpTimes()

    suspend fun insertWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity) =
        wakeUpTimeDao.insertWakeUpTime(wakeUpTimeEntity)

    suspend fun deleteWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity) =
        wakeUpTimeDao.deleteWakeUpTime(wakeUpTimeEntity)
}