package com.mobline.presentation.flow.main.content

import android.R
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobline.currencyconverter.presentation.databinding.FragmentMainPageBinding
import com.mobline.domain.model.currency.Rate
import com.mobline.domain.model.currency.RateExpanded
import com.mobline.presentation.base.BaseFragment
import com.mobline.presentation.common.EditTextTextWatcher
import com.mobline.presentation.common.ItemSelectedListener
import com.mobline.presentation.extensions.makeInvisible
import com.mobline.presentation.extensions.makeVisible
import com.mobline.presentation.flow.main.content.adapter.CurrencyRateAdapter
import com.mobline.presentation.tools.setSafeOnClickListener
import kotlin.reflect.KClass

class MainPageFragment :
    BaseFragment<GetRatesState, GetRatesEffect, MainPageViewModel, FragmentMainPageBinding>() {

    override val vmClazz: KClass<MainPageViewModel>
        get() = MainPageViewModel::class
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainPageBinding
        get() = FragmentMainPageBinding::inflate
    override val screenName: String
        get() = "main_page"

    private val currencyRateListAdapter by lazy {
        CurrencyRateAdapter(
            requireContext(),
            ArrayList()
        )
    }

    override val bindViews: FragmentMainPageBinding.() -> Unit = {
        initViews()
        initListeners()
        initSubscribers()
    }

    override fun observeState(state: GetRatesState) {
        binding.run {
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_dropdown_item,
                state.rates.map { it.currency }
            )

            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinner1.setAdapter(adapter)
            spinner2.setAdapter(adapter)

            etCurrency1.setText("")
            etCurrency2.setText("")
        }
    }

    //Initiates views
    private fun initViews() = with(binding) {
        exchangeRateList.layoutManager = LinearLayoutManager(requireContext())
        exchangeRateList.adapter = currencyRateListAdapter
    }

    //Initiates listeners
    private fun initListeners() = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }

        btnRefresh.setSafeOnClickListener {
            viewModel.loadRates()
        }

        etCurrency1.addTextChangedListener(object : EditTextTextWatcher() {
            override fun afterTextChange(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    if (viewModel.etFocus.value == Focus.FIRST) {
                        makeConversion(
                            etCurrency1,
                            etCurrency2,
                            viewModel.currency1.value,
                            viewModel.currency2.value
                        )
                    }
                } else etCurrency2.setText("")
            }
        })

        etCurrency2.addTextChangedListener(object : EditTextTextWatcher() {
            override fun afterTextChange(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    if (viewModel.etFocus.value == Focus.SECOND) {
                        makeConversion(
                            etCurrency2,
                            etCurrency1,
                            viewModel.currency2.value,
                            viewModel.currency1.value
                        )
                    }
                } else etCurrency1.setText("")
            }
        })

        spinner1.onItemSelectedListener = object : ItemSelectedListener() {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.currencyList.forEachIndexed { index, rate ->
                    if (p2 == index) viewModel.currency1.postValue(rate)
                }
            }
        }

        spinner2.onItemSelectedListener = object : ItemSelectedListener() {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.currencyList.forEachIndexed { index, rate ->
                    if (p2 == index) viewModel.currency2.postValue(rate)
                }
            }
        }

        etCurrency1.setOnFocusChangeListener { view, b ->
            viewModel.etFocus.value = if (b) Focus.FIRST else Focus.SECOND
        }

        etCurrency2.setOnFocusChangeListener { view, b ->
            viewModel.etFocus.value = if (b) Focus.SECOND else Focus.FIRST
        }
    }

    //Initiates subscribers
    private fun initSubscribers() = with(binding) {
        viewModel.generatedRateList.observe(viewLifecycleOwner) {
            currencyRateListAdapter.setData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { state ->
            setLoading(state)
        }

        viewModel.updateDateStr.observe(viewLifecycleOwner) {
            tvDate.text = it
        }

        viewModel.etFocus.observe(viewLifecycleOwner) {
            when (it) {
                Focus.FIRST -> {
                    etCurrency1.requestFocus()
                    etCurrency2.clearFocus()
                }
                Focus.SECOND -> {
                    etCurrency1.clearFocus()
                    etCurrency2.requestFocus()
                }
                else -> {
                    etCurrency1.clearFocus()
                    etCurrency2.clearFocus()
                }
            }
        }

        viewModel.currency1.observe(viewLifecycleOwner) {
            setCurrentRate(it, spinner1)

            viewModel.etFocus.postValue(Focus.FIRST)
            makeConversion(
                etFrom = etCurrency1,
                etTo = etCurrency2,
                rateFrom = it,
                rateTo = viewModel.currency2.value,
            )

            generateNewList(it, viewModel.state.value?.rates ?: listOf())
        }

        viewModel.currency2.observe(viewLifecycleOwner) {
            setCurrentRate(it, spinner2)

            viewModel.etFocus.postValue(Focus.SECOND)
            makeConversion(
                etFrom = etCurrency2,
                etTo = etCurrency1,
                rateFrom = it,
                rateTo = viewModel.currency1.value,
            )
        }
    }

    //Helper methods
    private fun makeConversion(etFrom: EditText, etTo: EditText, rateFrom: Rate?, rateTo: Rate?) {
        if (rateFrom == null || rateTo == null) {
            viewModel.logScreenInfo("Rates could not be null. Check the flow")
            return
        }

        etFrom.text.toString().let { text ->
            if (text.isNotBlank()) {
                val amount = text.toDouble()
                val converted =
                    viewModel.convert(rateFrom, rateTo, amount)

                etTo.setText(String.format("%.2f", converted))
                etTo.setSelection(etTo.text.length)
            }
        }
    }

    private fun setCurrentRate(rateCurrent: Rate, spinner: Spinner) {
        viewModel.currencyList.forEachIndexed { index, rate ->
            if (rateCurrent == rate) spinner.setSelection(index)
        }
    }

    private fun generateNewList(rateExclude: Rate, initalList: List<Rate>) {

        val generated = mutableListOf<RateExpanded>()

        initalList.forEach {
            if (rateExclude.currency != it.currency) {

                val amount = viewModel.convert(rateExclude, it, 1.0)

                generated.add(
                    RateExpanded(
                        cFrom = rateExclude.currency,
                        cTo = it.currency,
                        value = amount
                    )
                )
            }
        }

        viewModel.generatedRateList.postValue(generated)
    }

    private fun setLoading(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            lytRoot.alpha = 0.4F
            pbLoading.makeVisible()
        } else {
            lytRoot.alpha = 1F
            pbLoading.makeInvisible()
        }
        pbLoading.isIndeterminate = isLoading
        btnRefresh.isEnabled = !isLoading
        spinner1.isEnabled = !isLoading
        spinner2.isEnabled = !isLoading
        etCurrency1.isEnabled = !isLoading
        etCurrency2.isEnabled = !isLoading
    }
}