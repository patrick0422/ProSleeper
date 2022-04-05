package com.patrick0422.prosleeper.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patrick0422.prosleeper.base.BaseDiffUtil
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import com.patrick0422.prosleeper.data.local.WakeUpTimeTypeConverter
import com.patrick0422.prosleeper.databinding.ItemTempBinding

class StatisticsAdapter : RecyclerView.Adapter<StatisticsViewHolder>() {
    var itemList = listOf<WakeUpTimeEntity>()

    fun setData(newList: List<WakeUpTimeEntity>) {
        DiffUtil.calculateDiff(BaseDiffUtil(itemList, newList)).dispatchUpdatesTo(this)
        itemList = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder = StatisticsViewHolder.from(parent)
    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) = holder.bind(itemList[position])
    override fun getItemCount(): Int = itemList.size
}

class StatisticsViewHolder(
    private val binding: ItemTempBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): StatisticsViewHolder =
            StatisticsViewHolder(ItemTempBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun bind(wakeUpTimeEntity: WakeUpTimeEntity) = with(binding) {
        textDate.text = WakeUpTimeTypeConverter().localDateTimeToString(wakeUpTimeEntity.wakeUpTime)
    }
}