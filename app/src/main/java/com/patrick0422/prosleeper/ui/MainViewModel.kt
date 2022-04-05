package com.patrick0422.prosleeper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.patrick0422.prosleeper.data.Repository
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var isTodaysWakeUpTimeSaved = false

    fun getWakeUpTimes(): Flow<List<WakeUpTimeEntity>> = repository.getWakeUpTimes()

    fun insertWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity) = viewModelScope.launch {
        repository.insertWakeUpTime(wakeUpTimeEntity)
        isTodaysWakeUpTimeSaved = true
    }

    fun deleteWakeUpTime(wakeUpTimeEntity: WakeUpTimeEntity) = viewModelScope.launch {
        repository.deleteWakeUpTime(wakeUpTimeEntity)
    }
}