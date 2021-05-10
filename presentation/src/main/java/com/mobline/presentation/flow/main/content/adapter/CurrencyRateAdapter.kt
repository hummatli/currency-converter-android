package com.mobline.presentation.flow.main.content.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobline.currencyconverter.presentation.databinding.CurrencyRateItemBinding
import com.mobline.domain.model.currency.RateExpanded
import java.util.*

class CurrencyRateAdapter(
    private val mContext: Context,
    private val dataList: ArrayList<RateExpanded>?
) : RecyclerView.Adapter<CurrencyRateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CurrencyRateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: kotlin.run { 0 }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadData(dataList?.get(position))
    }


    class ViewHolder(val binding: CurrencyRateItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun loadData(rate: RateExpanded?) = with(binding) {
            tvCurrencies.text = "${rate?.cFrom.toString()}/${rate?.cTo.toString()}"
            tvExchangeRate.text = String.format("%.2f", rate?.value)
        }
    }

    fun setData(records: List<RateExpanded>) {
        dataList?.clear()
        dataList?.addAll(records)
        notifyDataSetChanged()
    }
}
