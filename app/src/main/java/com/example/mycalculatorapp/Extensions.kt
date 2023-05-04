package com.example.mycalculatorapp

fun String.formatEquation(equation: String) : String{
    if (this.isNotEmpty()){
        var result = this
        result += " $equation "
        return result
    }
    return ""
}