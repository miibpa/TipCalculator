package com.example.tipcalculator.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {
    lateinit var repository: TipCalculationRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp(){
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip(){
        val tip = TipCalculation(
            locationName = "Pancacke Paradise",
            checkAmount = 100.0, tipPct = 25, tipAmount = 15.0,grandTotal = 125.0)

        repository.saveTipCalculation(tip)

        assertEquals(tip,repository.loadTipCalculationByName(tip.locationName))

    }

    @Test
    fun testLoadSavedTipCalculations(){
        val tip1 = TipCalculation(
            locationName = "Pancacke Paradise",
            checkAmount = 100.0, tipPct = 25, tipAmount = 15.0,grandTotal = 125.0)
        val tip2 = TipCalculation(
            locationName = "Veggie Sensation",
            checkAmount = 100.0, tipPct = 25, tipAmount = 15.0,grandTotal = 125.0)

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        repository.loadSavedTipCalculations().observeForever{ tipCalculations ->
            assertEquals(2,tipCalculations?.size)

        }
    }

}