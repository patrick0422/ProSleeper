package com.patrick0422.prosleeper.ui.statistics

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.databinding.FragmentStatisticsBinding
import com.patrick0422.prosleeper.ui.MainViewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val statisticsAdapter by lazy { StatisticsAdapter() }


    override fun init() = with(binding) {
        recyclerView.adapter = statisticsAdapter

        getWakeUpTimes()
    }

    private fun getWakeUpTimes() {
        mainViewModel.getWakeUpTimes().asLiveData().observe(viewLifecycleOwner) {
            statisticsAdapter.setData(it)
        }
    }
}