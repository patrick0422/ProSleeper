package com.patrick0422.prosleeper.ui.statistics

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import com.patrick0422.prosleeper.databinding.FragmentStatisticsBinding
import com.patrick0422.prosleeper.ui.MainViewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val entries = mutableListOf<Entry>()

    override fun init() = with(binding) {
        getWakeUpTimes()
        buttonTest.setOnClickListener { addItem() }
        chart.invalidate()
    }

    private fun getWakeUpTimes() {
        mainViewModel.getWakeUpTimes().asLiveData().observe(viewLifecycleOwner) { result ->
//            setChart(result)
            testChart()
        }
    }

    private fun addItem() {
        entries.add(Entry(entries.size.toFloat(), (1..24).random().toFloat()))
        binding.chart.apply {
            data = LineData(LineDataSet(entries, "Test"))
            invalidate()
        }
    }

    private fun testChart() {
        binding.chart.apply {
            data = LineData(LineDataSet(entries, "Test"))
            invalidate()
        }
    }

    private fun setChart(result: List<WakeUpTimeEntity>) {
        val entries = result.map {
            Entry(it.id.toFloat(), it.wakeUpTime.minute.toFloat())
        }.toMutableList()

        val dataSet = LineDataSet(entries, "Test")

        binding.chart.apply {
            xAxis.valueFormatter = DateAxisValueFormat()
            xAxis.setDrawLabels(true)
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 24f
            

            data = LineData(dataSet)
            invalidate()
        }
    }
}

class DateAxisValueFormat: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return super.getFormattedValue(value)
    }
}