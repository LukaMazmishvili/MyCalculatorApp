package com.example.mycalculatorapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun add(n1: Double, n2: Double): Double {
        return (n1 + n2)
    }

    fun minus(n1: Double, n2: Double): Double {
        return (n1 - n2)
    }

    fun multiply(n1: Double, n2: Double): Double {
        return n1 * n2
    }

    fun divide(n1: Double, n2: Double): Double {
        return n1 / n2
    }
}