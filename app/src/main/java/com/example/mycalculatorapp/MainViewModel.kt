package com.example.mycalculatorapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _result = MutableStateFlow<String>("")
    val result = _result.asStateFlow()

    fun setResult(result: String){
        _result.value = result
    }

    fun add(n1: Double, n2: Double) {
        _result.value = (n1 + n2).toString()
        println("valueInAddFunc1 : " + n1.toString())
        println("valueInAddFunc2 : " + n2.toString())
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