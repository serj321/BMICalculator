package com.example.bmicalculatorkotlin.body

import kotlin.math.pow

class bodyCalculations {
    public fun calculateBMI(height: Double, weight: Double): Double{
        var BMI : Double
        BMI = (weight / (( height/ 100).pow(2)))
        return BMI
    }
}