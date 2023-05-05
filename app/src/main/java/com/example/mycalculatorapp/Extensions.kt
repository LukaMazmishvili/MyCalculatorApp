package com.example.mycalculatorapp

fun String.formatEquation(equation: String = "") : String {
    if (this.isNotEmpty() && !this.endsWith(" ")){
        var result = this
        result += " $equation "
        return result
    } else if (this.endsWith(" ")) {
        var result = this
        result = result.dropLast(3)
        if (equation.isNotEmpty()){
            result += " $equation "
        }
        return result
    }
    return ""
}