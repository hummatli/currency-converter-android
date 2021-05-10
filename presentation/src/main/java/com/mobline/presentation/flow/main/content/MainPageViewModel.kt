package com.mobline.presentation.flow.main.content

import androidx.lifecycle.MutableLiveData
import com.mobline.domain.model.currency.Currency
import com.mobline.domain.model.currency.Rate
import com.mobline.domain.model.currency.RateExpanded
import com.mobline.domain.usecase.currency.GetRatesUseCase
import com.mobline.domain.usecase.logs.LogScreenInfoUseCase
import com.mobline.presentation.base.BaseViewModel
import com.mobline.presentation.tools.Utils
import java.util.*

enum class Focus { FIRST, SECOND }

class MainPageViewModel(
    private val getRatesUseCase: GetRatesUseCase,
    logScreenInfoUseCase: LogScreenInfoUseCase,
) : BaseViewModel<GetRatesState, GetRatesEffect>(logScreenInfoUseCase) {

    lateinit var currencyList: List<Rate>
    val currency1 = MutableLiveData<Rate>()
    val currency2 = MutableLiveData<Rate>()
    val updateDateStr = MutableLiveData<String>()

    val generatedRateList = MutableLiveData<List<RateExpanded>>()

    val etFocus = MutableLiveData<Focus>()

    init {
        loadRates()
    }

    fun loadRates() {
        getRatesUseCase.launch(Unit) {
            onSuccess = {
                postState(GetRatesState(it.rates))
                currencyList = it.rates


                currencyList.filter { it.currency == Currency.USD }.getOrNull(0)?.let {
                    currency1.postValue(it)
                }

                currencyList.filter { it.currency == Currency.EUR }.getOrNull(0)?.let {
                    currency2.postValue(it)
                }

                updateDateStr.postValue(Utils.millisToDate(Date().time))
            }

            onError = {
                postEffect(GetRatesEffect.ShowError)
            }
        }
    }

    fun convert(cFrom: Rate, cTo: Rate, amount: Double): Double {
        return amount * cTo.value / cFrom.value
    }
}


sealed class GetRatesEffect {
    object ShowError : GetRatesEffect()
}

class GetRatesState(val rates: List<Rate>)