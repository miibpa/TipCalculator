package com.example.tipcalculator.model

import java.time.temporal.TemporalAmount

data class TipCalculation(
    val locationName: String = "",
    val checkAmount: Double = 0.0,
    val tipPct: Int = 0,
    val tipAmount: Double = 0.0,
    val grandTotal: Double = 0.0
)