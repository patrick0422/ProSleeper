package com.patrick0422.prosleeper.ui.statistics

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import com.patrick0422.prosleeper.databinding.FragmentStatisticsBinding
import com.patrick0422.prosleeper.ui.MainViewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun init() = with(binding) {
        getWakeUpTimes()
    }

    private fun getWakeUpTimes() {
        mainViewModel.getWakeUpTimes().asLiveData().observe(viewLifecycleOwner) { result ->
            setChart(result)
        }
    }

    private fun setChart(result: List<WakeUpTimeEntity>) {
        val entries = result.map {
            BarEntry(it.id.toFloat(), it.wakeUpTime.minute.toFloat())
        }.toMutableList()

        val barDataSet = BarDataSet(entries, "Test")

        binding.barChart.apply {
            xAxis.valueFormatter = DateAxisValueFormat()
            xAxis.setDrawLabels(true)
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 24f
            

            data = BarData(barDataSet)
            invalidate()
        }
    }
}

class DateAxisValueFormat: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return super.getFormattedValue(value)
    }
}