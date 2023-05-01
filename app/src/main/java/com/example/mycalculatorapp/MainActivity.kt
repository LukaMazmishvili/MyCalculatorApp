package com.example.mycalculatorapp

//import androidx.activity.viewModels
import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.mycalculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        themeSwitchView()

        val uiModeManager = this.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

        binding.ivDarkMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.ivLightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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
}