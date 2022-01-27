package com.example.bmicalculatorkotlin

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculatorkotlin.body.bodyCalculations
import com.example.bmicalculatorkotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.unitOptions
        binding.calculateButton.setOnClickListener{ calculateBMIClick() }
        binding.optionMetric.setOnClickListener { setUpMetric() }
        binding.optionImperial.setOnClickListener { setUpImperial() }
    }

    private fun calculateBMIClick(){
        var inches : Double

        if(inputValidation()){
            return
        }

        var height : Double = binding.heightEditText.text.toString().toDouble()
        var weight : Double = binding.weightEditText.text.toString().toDouble()

        if(binding.unitOptions.checkedRadioButtonId == R.id.option_metric){
            displayBMI(bodyCalculations().calculateBMI(height, weight))
        }else{
            if(TextUtils.isEmpty(binding.heightInchEditText.text.toString())){
                inches = 0.0
            } else{
                inches =  binding.heightInchEditText.text.toString().toDouble()
            }

            height = (((height * 12) + inches) * 2.54)
            weight *= 0.45359237
            displayBMI(bodyCalculations().calculateBMI(height, weight))
        }
    }

    private fun displayBMI(calculatedBMI : Double){
        var calculatedBMIFormated:Double = String.format("%.2f", calculatedBMI).toDouble()
        binding.BMIResult.text = getString(R.string.BMI_result, calculatedBMIFormated.toString())
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun setUpMetric(){
        binding.height.hint = getString(R.string.cm)
        binding.weight.hint = getString(R.string.kg)
        binding.heightInc.visibility = View.INVISIBLE
        clearTextBoxes()
    }

    private fun setUpImperial(){
        binding.height.hint = getString(R.string.ft)
        binding.weight.hint = getString(R.string.lb)
        binding.heightInc.visibility = View.VISIBLE
        clearTextBoxes()
    }

    private fun invalidInputWarning(invalidMessage: String){
        Toast.makeText(this, invalidMessage, Toast.LENGTH_SHORT).show()
    }

    private fun clearTextBoxes(){
        binding.heightInchEditText.text?.clear()
        binding.heightEditText.text?.clear()
        binding.weightEditText.text?.clear()
    }



    private fun inputValidation(): Boolean{
        if(TextUtils.isEmpty(binding.heightEditText.text.toString()) && TextUtils.isEmpty(binding.weightEditText.text.toString())){
            invalidInputWarning("Please input a height and weight")
            return true
        } else if(TextUtils.isEmpty(binding.heightEditText.text.toString())){
            invalidInputWarning("Please input a height")
            return true
        } else if(TextUtils.isEmpty(binding.weightEditText.text.toString())){
            invalidInputWarning("Please input a weight")
            return true
        }
        var height = binding.heightEditText.text.toString().toDouble()
        var weight = binding.weightEditText.text.toString().toDouble()

        if(height <= 0 || weight <= 0){
            invalidInputWarning("No zeros and negative numbers!")
            return true
        }

        if(binding.unitOptions.checkedRadioButtonId == R.id.option_imperial){
            if(!TextUtils.isEmpty(binding.heightInchEditText.text.toString())){
                var heightInch = binding.heightInchEditText.text.toString().toDouble()

                if(heightInch > 11){
                    invalidInputWarning("You can't have more than 11 inches")
                    return true
                } else if (heightInch <= 0){
                    invalidInputWarning("No negative numbers!")
                }
            }
        }

        return false
    }
}