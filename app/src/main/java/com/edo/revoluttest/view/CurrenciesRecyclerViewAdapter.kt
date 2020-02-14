package com.edo.revoluttest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.edo.revoluttest.R
import com.edo.revoluttest.di.app.Injector
import com.edo.revoluttest.service.data.Currency
import com.edo.revoluttest.service.model.CircleTransform
import com.edo.revoluttest.utils.CurrenciesDiffUtil
import com.edo.revoluttest.utils.FlagsUtils
import com.edo.revoluttest.viewmodel.CurrenciesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_currencies.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class CurrenciesRecyclerViewAdapter() : RecyclerView.Adapter<CurrenciesRecyclerViewAdapter.ViewHolder>() {

    @Inject
    lateinit var viewModel: CurrenciesViewModel

    @Inject
    lateinit var context: Context

    private var currencies = ArrayList<Currency>()

    private var flags = FlagsUtils().init().flags

    fun setCurrenciesList(currencies: ArrayList<Currency>) {
        val diffCallback = CurrenciesDiffUtil(this.currencies, currencies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.currencies = currencies
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_currencies, parent, false)
        Injector.appComponent.inject(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        holder.view.name.text = currency.name
        holder.view.code.text = currency.currencyCode
        val rate = "%.2f".format(Locale.ENGLISH, currency.rate)//BigDecimal(currency.rate).setScale(2, RoundingMode.HALF_EVEN).toString()
        val flag = flags.get(currency.currencyCode) ?: ""
        if (!flag.isEmpty()) {
            Picasso.get()
                .load(flag)
                .transform(CircleTransform())
                .into(holder.view.flag)
        }

        holder.view.rate.setText(rate)
        holder.view.setOnClickListener {
            newCurrency(currency)
        }

    }

    private fun newCurrency(newCurrency: Currency) {
        viewModel.getBaseCurrencySubject().onNext(newCurrency)
        viewModel.getLatestSubject().onNext(newCurrency)
    }

    override fun getItemCount(): Int = currencies.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
