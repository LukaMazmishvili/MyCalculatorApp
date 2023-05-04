package com.example.mycalculatorapp

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mycalculatorapp.Operations.*
import com.example.mycalculatorapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var operation = ""
    private var n1 = 0.0
    private var n2 = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivDarkMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.ivLightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        listeners()

    }

    private fun listeners() {
        with(binding) {
            tvAC.setOnClickListener(this@MainActivity)
            tvDivision.setOnClickListener(this@MainActivity)
            ivPlusMinus.setOnClickListener(this@MainActivity)
            tvEqual.setOnClickListener(this@MainActivity)
            tvMinus.setOnClickListener(this@MainActivity)
            tvMultiply.setOnClickListener(this@MainActivity)
            tvPlus.setOnClickListener(this@MainActivity)
            ivDeleteLast.setOnClickListener(this@MainActivity)
            tvPoint.setOnClickListener(this@MainActivity)
            tvPercentage.setOnClickListener(this@MainActivity)
            tvN1.setOnClickListener(this@MainActivity)
            tvN2.setOnClickListener(this@MainActivity)
            tvN3.setOnClickListener(this@MainActivity)
            tvN4.setOnClickListener(this@MainActivity)
            tvN5.setOnClickListener(this@MainActivity)
            tvN6.setOnClickListener(this@MainActivity)
            tvN7.setOnClickListener(this@MainActivity)
            tvN8.setOnClickListener(this@MainActivity)
            tvN9.setOnClickListener(this@MainActivity)
            tvN0.setOnClickListener(this@MainActivity)
        }
    }

    private fun calculate(equation: String, result: String, operation: String, n2FromEqual: Double = 0.0) {
        var processEnded = false
        when (operation) {
            "+" -> {
//                n1 += number1.toDouble()
                viewModel.add(n1, n2)
                processEnded = true
            }
            "-" -> {
//                n1 -= result.toDouble()
                viewModel.minus(n1, n2)
                processEnded = true
            }
            "÷" -> {
//                n1 /= result.toDouble()
                viewModel.divide(n1, n2)
                processEnded = true
            }
            "×" -> {
                println("multiply : " + operation)
//                n1 *= result.toDouble()
                viewModel.multiply(n1, n2)
                processEnded = true
            }
            "=" -> {
//                n2 = n2FromEqual
                processEnded = true
            }
        }
        if (processEnded) {
            showResult(equation, result, operation)
            processEnded = false
        }
    }

    private fun showResult(equation: String, result: String, operation: String = "") {

        var res = ""
        if (operation != "=") {
            viewModel.setResult(result)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect() {
                    res = it
                }
            }
        }

        with(binding) {
            tvOperation.text = equation
            if (res.endsWith(".0")) {
                tvResult.text = res.dropLast(2)
            } else {
                tvResult.text = res
            }
        }
    }

    private fun themeSwitchView() {

        val uiModeManager = this.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isDarkMode = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES

        if (!isDarkMode) {
            with(binding) {
                ivDarkMode.background.setTint(
                    ContextCompat.getColorStateList(
                        this@MainActivity.applicationContext,
                        R.color.inactiveThemeBtn
                    )!!.defaultColor
                )
                ivLightMode.background.setTint(
                    ContextCompat.getColorStateList(
                        this@MainActivity.applicationContext,
                        R.color.activeThemeBtn
                    )!!.defaultColor
                )
            }
        } else {
            with(binding) {
                ivLightMode.background.setTint(
                    ContextCompat.getColorStateList(
                        this@MainActivity.applicationContext,
                        R.color.inactiveThemeBtn
                    )!!.defaultColor
                )
                ivDarkMode.background.setTint(
                    ContextCompat.getColorStateList(
                        this@MainActivity.applicationContext,
                        R.color.activeThemeBtn
                    )!!.defaultColor
                )
            }
        }
    }

    override fun onClick(clickedView: View?) {

        var equation = binding.tvOperation.text.toString()
        var result = binding.tvResult.text.toString()

        fun checkNum1(operation: String, operator: String, result: String){
            when (operation) {
                PLUS.operator -> {
                    println(operation + " : " + operator)
                    if (operation == operator) {
                        n1 += result.toDouble()
                    } else if (operation.isEmpty()) {
                        n1 = result.toDouble()
                    }
                }
                MINUS.operator -> {
                    println("minus : " + operation + " : " + operator)
                    if (operation == operator) {
                        n1 -= result.toDouble()
                    } else if (operation.isEmpty()) {
                        n1 = result.toDouble()
                    }
                }
                MULTIPLY.operator -> {
                    println(operation + " : " + operator)
                    if (operation == operator) {
                        n1 *= result.toDouble()
                    } else if (operation.isEmpty()) {
                        n1 = result.toDouble()
                    }
                }
                DIVIDE.operator -> {
                    println(operation + " : " + operator)
                    if (operation == operator) {
                        n1 /= result.toDouble()
                    } else if (operation.isEmpty()) {
                        n1 = result.toDouble()
                    }
                }
            }
        }

        with(binding) {
            when (clickedView) {
                tvAC -> {
                    equation = ""
                    result = ""
                    operation = ""
                    showResult(equation, result, operation)
                }
                ivDeleteLast -> {
                    if (result.isNotEmpty()) {
                        showResult(equation.dropLast(1), result.dropLast(1))
                    } else {
                    }
                }
                tvDivision -> {
                    if (result.isNotEmpty() && equation.isNotEmpty()) {
                        checkNum1(operation, DIVIDE.operator, result)
                        n2 = tvResult.text.toString().toDouble()
                        operation = DIVIDE.operator
                        calculate(equation.formatEquation(DIVIDE.operator), result = "", operation)
                    } else {
                    }
                }
                tvMinus -> {
                    if (result.isNotEmpty() && equation.isNotEmpty()) {
                        checkNum1(operation, MINUS.operator, result)
                        operation = MINUS.operator
                        n2 = result.toDouble()
                        Log.d("n1", "minus : n1 : " + n1)
                        Log.d("n2", "minus : n2 : " + n2)
                        calculate(equation.formatEquation(MINUS.operator), result = "", operation)
                    } else {
                    }
                }
                tvPlus -> {
                    if (result.isNotEmpty() && equation.isNotEmpty()) {
                        checkNum1(operation, PLUS.operator, result)
                        operation = PLUS.operator
                        n2 = tvResult.text.toString().toDouble()
                        calculate(equation.formatEquation(PLUS.operator), result = "", operation)
                    } else { }

//                    if (result.isNotEmpty()) {
//                        if (operation == "+") {
//                            n1 += tvResult.text.toString().toDouble()
//                        } else if (operation.isEmpty()) {
//                            n1 = tvResult.text.toString().toDouble()
//                        }
//                        operation = "+"
//                        if (equation.isNotEmpty()) {
//                            equation += " + "
//                            n2 = tvResult.text.toString().toDouble()
//                            viewModel.add(n1, n2)
//                            result = ""
//                        }
//                        showResult(equation, result, operation)
//                    } else {}
                }
                tvMultiply -> {
                    if (result.isNotEmpty() && equation.isNotEmpty()) {
                        checkNum1(operation, MULTIPLY.operator, result)
                        n2 = tvResult.text.toString().toDouble()
                        operation = MULTIPLY.operator
                        calculate(equation.formatEquation(MULTIPLY.operator), result = "", operation)
                    } else {
                    }
                }
                tvPercentage -> {}
                tvEqual -> {
                    if (result.isNotEmpty()) {
                        n2 = tvResult.text.toString().toDouble()
                        when (operation) {
                            "+" -> {
                                operation = "="
                                viewModel.add(n1, n2)
                                showResult(equation, result, operation)
                            }
                            else -> {}
                        }
                    } else {

                    }
                }
                tvPoint -> {
                    if (equation.isNotEmpty()
                        && result.isNotEmpty()
                        && !result.contains(".")
                    ) {
                        equation += "."
                        result += "."
                        showResult(equation, result, operation)
                    } else {
                    }
                }
                tvN0 -> {
                    if (result != "0") {
                        result += "0"
                        equation += "0"
                        showResult(equation, result, operation)
                    } else {
                    }
                }
                tvN1, tvN2, tvN3, tvN4, tvN5, tvN6, tvN7, tvN8, tvN9 -> {
                    if (clickedView is TextView) {
                        if (operation == "=") {
                            equation = ""
                            result = ""
                            operation = ""
                        }
                        result += clickedView.text.toString()
                        equation += clickedView.text.toString()
                        showResult(equation, result, operation)
                    } else {
                    }
                }
                else -> {}
            }
        }
    }
}