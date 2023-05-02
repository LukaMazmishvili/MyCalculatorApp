package com.example.mycalculatorapp

import androidx.activity.viewModels
import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mycalculatorapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var operation = ""
    private var n1 = 0.0
    private var n2 = 0.0


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(binding.root)

//        themeSwitchView()

        val uiModeManager = this.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

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

    private fun showResult(equation: String, result: String, operation: String) {

        var res = ""
        if (operation != "=") {
            viewModel.setResult(result)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect() {
                    res = it
                    println("resultFromVM : " + it)
                }
            }
        }

        with(binding) {
            tvOperation.setText(equation)
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
                        showResult(equation.dropLast(1), result.dropLast(1), operation)
                    } else {

                    }
                }
                tvDivision -> {
                    if (operation.isEmpty())
                        n1 = result.toDouble()
                    operation = "÷"
                    result = ""
                    if (equation.isNotEmpty() && equation.last().toString() != " ") {
                        equation += " ÷ "
                    }
                    showResult(equation, result, operation)
                }
                tvMinus -> {
                    if (operation.isEmpty())
                        n1 += result.toDouble()
                    operation = "-"
                    result = ""
                    if (equation.isNotEmpty()) {
                        equation += " - "
                    }
                    showResult(equation, result, operation)
                }
                tvPlus -> {
                    if (result.isNotEmpty()){
                        if(operation == "+") {
                            n1 += tvResult.text.toString().toDouble()
                        }else if (operation.isEmpty()) {
                            n1 = tvResult.text.toString().toDouble()
                        }
                        operation = "+"
                        println("operation Plus: " + tvResult.text.toString().toDouble().toString())
                        if (equation.isNotEmpty()) {
                            equation += " + "
                            n2 = tvResult.text.toString().toDouble()
                            viewModel.add(n1, n2)
                            result = ""
                        }
                        showResult(equation, result, operation)
                    } else {

                    }
                }
                tvPercentage -> {
                    Log.d("operaciaArChans", operation)
                }
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
//                        && !equation.contains(".")
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
                        result += clickedView.text.toString()
                        equation += clickedView.text.toString()
                        println("operationOnNumber" + operation)
                        showResult(equation, result, operation)
                    } else {
                    }
                }
                else -> {}
            }
        }
    }
}