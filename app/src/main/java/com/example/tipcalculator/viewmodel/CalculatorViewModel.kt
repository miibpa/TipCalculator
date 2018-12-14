package com.example.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.example.tipcalculator.R
import com.example.tipcalculator.model.Calculator
import com.example.tipcalculator.model.TipCalculation

class CalculatorViewModel (app: Application, val calculator: Calculator = Calculator()) : AndroidViewModel(app) {

    private var lastTipCalculated = TipCalculation()
    var inputCheckAmount = MutableLiveData<String>()
    var inputTipPercentage = MutableLiveData<String>()

    var outputCheckAmount=  MutableLiveData<String>()
    var outputTipAmount = MutableLiveData<String>()
    var outputTotalDollarAmount = MutableLiveData<String>()

    var locationName = MutableLiveData<String>()


    init {
        updateOutputs(lastTipCalculated)
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        lastTipCalculated = tipCalculation
        outputCheckAmount.value = getApplication<Application>().getString(R.string.dollar_amount,tipCalculation.checkAmount)
        outputTipAmount.value = getApplication<Application>().getString(R.string.dollar_amount,tipCalculation.tipAmount)
        outputTotalDollarAmount.value = getApplication<Application>().getString(R.string.dollar_amount,tipCalculation.grandTotal)
        locationName.value = tipCalculation.locationName
    }


    fun saveCurrentTip(name: String){

        val tipToSave = lastTipCalculated.copy(locationName = name)

        calculator.saveTipCalculation(tipToSave)

        updateOutputs(tipToSave)


    }

    fun calculateTip(){
        val checkAmount = inputCheckAmount.value?.toDoubleOrNull()
        val tipPct = inputTipPercentage.value?.toIntOrNull()
        if(checkAmount != null && tipPct != null){
            updateOutputs(calculator.calculateTip(checkAmount,tipPct))
        }
    }

    fun loadSavedTipCalculationSummaries() : LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedTipCalculations()) { tipCalculationObjects ->
            tipCalculationObjects.map {
                TipCalculationSummaryItem(it.locationName,
                    getApplication<Application>().getString(R.string.dollar_amount, it.grandTotal))
            }
        }
    }

    fun loadTipCalculation(name: String) {

        val tc = calculator.loadTipCalculationByLocationName(name)

        if (tc != null) {
            inputCheckAmount.value = tc.checkAmount.toString()
            inputTipPercentage.value = tc.tipPct.toString()

            updateOutputs(tc)
        }
    }

    open class Factory(val application: Application, val calculator: Calculator = Calculator()) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CalculatorViewModel(application,calculator) as T
        }

    }

}