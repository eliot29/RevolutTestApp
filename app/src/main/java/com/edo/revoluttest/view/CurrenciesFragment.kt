package com.edo.revoluttest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edo.revoluttest.R
import com.edo.revoluttest.di.app.Injector
import com.edo.revoluttest.service.data.Currency
import com.edo.revoluttest.service.model.CircleTransform
import com.edo.revoluttest.utils.FlagsUtils
import com.edo.revoluttest.viewmodel.CurrenciesViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_currencies.view.*
import kotlinx.android.synthetic.main.fragment_currencies_list.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CurrenciesFragment.OnListFragmentInteractionListener] interface.
 */
class CurrenciesFragment : Fragment() {

    @Inject
    lateinit var viewModel: CurrenciesViewModel

    @Inject
    lateinit var currencyAdapter: CurrenciesRecyclerViewAdapter

    private var currencyDisposable: Disposable? = null

    lateinit var currencyDefault: Currency

    private var flags = FlagsUtils().init().flags

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.init()
        return inflater.inflate(R.layout.fragment_currencies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyDefault = viewModel.currencyDefault
        code.text = currencyDefault.currencyCode
        name.text = currencyDefault.name
        setFlag(currencyDefault.currencyCode)
        rate.setText("%.2f".format(Locale.ENGLISH, currencyDefault.rate))
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = currencyAdapter
        viewModel.getCurrenciesSubject()
            .doOnNext {
                currencyAdapter.setCurrenciesList(it as ArrayList<Currency>)
                list.setItemViewCacheSize(it.size)
            }
            .subscribe()

        currencyDisposable = RxTextView.textChanges(rate).subscribe{ text ->
            newRate(text.toString().toDouble())
        }

        viewModel.getLatestSubject()
            .doOnNext {
                code.text = it.currencyCode
                name.text = it.name
                setFlag(it.currencyCode)
                currencyDisposable?.dispose()
                rate.setText("%.2f".format(Locale.ENGLISH, it.rate))
                currencyDisposable = getTextChangeListener()
            }
            .subscribe()
    }

    fun setFlag(code: String) {
        val flagUrl = flags.get(code) ?: ""
        if (flagUrl.isNotEmpty()) {
            Picasso.get()
                .load(flagUrl)
                .transform(CircleTransform())
                .into(flag)
        }
    }

    fun getTextChangeListener(): Disposable? {
        return RxTextView.textChanges(rate).subscribe{ text ->
            newRate(text.toString().toDouble())
        }
    }

    fun newRate(newRate: Double) {
        viewModel.getBaseCurrencySubject().onNext(
            Currency(
                currencyDefault.currencyCode,
                newRate,
                currencyDefault.name
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.rates)
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
        currencyDisposable?.dispose()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrenciesFragment()
    }

}
