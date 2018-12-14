package com.example.tipcalculator.viewmodel

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tipcalculator.R
import com.example.tipcalculator.model.Calculator
import com.example.tipcalculator.model.TipCalculation
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTests {

    lateinit var calculatorViewModel: CalculatorViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Mock
    lateinit var mockCalculator: Calculator

    @Mock
    lateinit var application: Application

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        stubResource(0.0,"$0.00")
        calculatorViewModel = CalculatorViewModel(application,mockCalculator)
    }

    private fun stubResource(given:Double,returnStub:String){
        `when` (application.getString(R.string.dollar_amount,given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTip(){
        calculatorViewModel.inputCheckAmount.value = "10.00"
        calculatorViewModel.inputTipPercentage.value = "15"

        val stub = TipCalculation(checkAmount = 10.00,tipAmount = 1.5, grandTotal = 11.5)
        `when`(mockCalculator.calculateTip(10.00,15)).thenReturn(stub)
        stubResource(10.0,"$10.00")
        stubResource(1.50,"$1.50")
        stubResource(11.50,"$11.50")
        calculatorViewModel.calculateTip()

        assertEquals("$10.00",calculatorViewModel.outputCheckAmount.value)
        assertEquals("$1.50",calculatorViewModel.outputTipAmount.value)
        assertEquals("$11.50",calculatorViewModel.outputTotalDollarAmount.value)

    }

    @Test
    fun testCalculateTipBadTipPercent(){
        calculatorViewModel.inputCheckAmount.value = "10.00"
        calculatorViewModel.inputTipPercentage.value = ""

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(),anyInt())

    }

    @Test
    fun testCalculateTipBadCheckInputAmount() {

        calculatorViewModel.inputCheckAmount.value = ""
        calculatorViewModel.inputTipPercentage.value = "15"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(),anyInt())


    }


    @Test
    fun testSaveCurrentTip(){
        val stub = TipCalculation(checkAmount = 10.0, tipAmount = 1.5, grandTotal = 11.5)
        val stubLocationName = "Test Name"

        fun setupTipCalculation() {
            calculatorViewModel.inputCheckAmount.value = "10.00"
            calculatorViewModel.inputTipPercentage.value = "15"

            `when`(mockCalculator.calculateTip(10.00,15)).thenReturn(stub)
            stubResource(10.0,"$10.00")
            stubResource(1.50,"$1.50")
            stubResource(11.50,"$11.50")
        }

        setupTipCalculation()
        calculatorViewModel.calculateTip()

        calculatorViewModel.saveCurrentTip(stubLocationName)

        verify(mockCalculator).saveTipCalculation(stub.copy(locationName = stubLocationName))

        assertEquals(stubLocationName, calculatorViewModel.locationName.value)
    }


}