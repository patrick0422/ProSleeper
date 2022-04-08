package com.patrick0422.prosleeper.ui.statistics

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.github.mikephil.charting.data.*
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import com.patrick0422.prosleeper.databinding.FragmentStatisticsBinding
import com.patrick0422.prosleeper.ui.MainViewModel
import com.patrick0422.prosleeper.util.DateAxisValueFormatter
import com.patrick0422.prosleeper.util.TimeAxisValueFormatter
import java.time.LocalDateTime
import java.time.LocalTime

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() = with(binding) {
        getWakeUpTimes()
//        getMockedWakeUpTimes()

        buttonTest.setOnClickListener { getMockedWakeUpTimes() }
    }

    private fun getWakeUpTimes() {
        mainViewModel.getWakeUpTimes().asLiveData().observe(viewLifecycleOwner) { result ->
            setChart(result)
        }
    }

    private fun getMockedWakeUpTimes() {
        val result = mutableListOf<WakeUpTimeEntity>()

        for (i in 1..10) {
            result.add(WakeUpTimeEntity(LocalDateTime.of(2022, 4, i, 7, (20..40).random(), 39)).apply { id = i })
        }

        setChart(result)
    }

    private fun setChart(result: List<WakeUpTimeEntity>) {
        val wakeUpTimeList = result.map { wakeUpTimeEntity ->
            Entry(
                wakeUpTimeEntity.wakeUpTime.dayOfYear.toFloat(),
                (wakeUpTimeEntity.wakeUpTime.hour * 60 + wakeUpTimeEntity.wakeUpTime.minute).toFloat()
            )
        }.toMutableList()

        val dataSet = LineDataSet(wakeUpTimeList, "Wake-up Time").apply {
            setDrawFilled(true)
            fillColor = resources.getColor(R.color.yellow, null)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        binding.chart.apply {
            description = null
            xAxis.valueFormatter = DateAxisValueFormatter()
            TimeAxisValueFormatter().let {
                axisLeft.valueFormatter = it
                axisRight.valueFormatter = it
            }
            axisLeft.apply {
                val average = result.map {
                    (it.wakeUpTime.hour * 60 + it.wakeUpTime.minute)
                }.average()

                binding.textAverageWakeUpTime.text = LocalTime.ofSecondOfDay((average * 60).toLong()).toString()

//                axisMinimum = average.toFloat() - 50F
//                axisMaximum = average.toFloat() + 50F
            }
            data = LineData(dataSet)
            invalidate()
        }
    }
}