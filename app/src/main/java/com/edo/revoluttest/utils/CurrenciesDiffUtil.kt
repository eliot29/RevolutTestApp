package com.edo.revoluttest.utils

import androidx.recyclerview.widget.DiffUtil
import com.edo.revoluttest.service.data.Currency

class CurrenciesDiffUtil(private val oldCurrencies: List<Currency>,
                         private val newCurrencies: List<Currency>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencies[oldItemPosition].currencyCode == newCurrencies[newItemPosition].currencyCode
    }

    override fun getOldListSize(): Int {
        return oldCurrencies.size
    }

    override fun getNewListSize(): Int {
        return newCurrencies.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencies[oldItemPosition].rate == newCurrencies[newItemPosition].rate
    }
}