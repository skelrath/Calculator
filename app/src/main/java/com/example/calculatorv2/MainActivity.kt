package com.example.calculatorv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.Toast
import com.example.calculatorv2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var expression: String = ""

    private var canAddOperation = false
    private var canAddDecimal = true
    private var canAddComma = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val numbersIds = binding.numbers.referencedIds

        for (id in numbersIds) {

            var buttonText: String

            (binding.root.findViewById<Button>(id)).setOnClickListener {

                if (binding.resultTV.text.length > 9) {
                    canAddDecimal = false
                }

                if (canAddDecimal) {

                    buttonText = (it as Button).text.toString()

                    expression += buttonText

                    if (canAddOperation) {

                        binding.resultTV.append(buttonText)

                    } else {

                        binding.resultTV.text = buttonText

                    }

                    if (binding.resultTV.text.isNotEmpty()) {
                        canAddComma = true

                    }
                    canAddOperation = true


                } else {
                    Toast.makeText(
                        applicationContext,
                        "Нельзя ввести число!",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }
        }


        val operationsIds = binding.operators.referencedIds

        for (id in operationsIds) {

            var buttonText: String

            (binding.root.findViewById<Button>(id)).setOnClickListener {
                if (it == binding.comma) {

                    if (canAddComma) {
                        binding.resultTV.append(".")
                        canAddComma = false
                        expression += "."
                    }
                    return@setOnClickListener
                }

                if (canAddOperation) {

                    buttonText = (it as Button).text as String
                    canAddDecimal = true

                    when (it) {

                        binding.plusMinus -> {

                            val result = (binding.resultTV.text.toString().toDouble() * -1)

                            binding.resultTV.text =
                                if (result % 1 == 0.0) result.toInt().toString()
                                else result.toString()

                            return@setOnClickListener

                        }

                        binding.percent -> {

                            expression += "%"
                            binding.resultTV.append("%")

                            canAddDecimal = false

                            return@setOnClickListener
                        }

                        else -> expression += it.text as String

                    }

                    binding.resultTV.text = buttonText
                    canAddOperation = false


                } else {

                    Toast.makeText(
                        applicationContext,
                        "Нельзя ввести оператор!",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }
        }

        binding.equal.setOnClickListener {

            canAddDecimal = true

            if (!(expression[expression.lastIndex].isDigit() || expression[expression.lastIndex] == '%')) {
                Toast.makeText(applicationContext, "Введите второе число!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val total = ExpressionSolver.eval(expression)

            if (total % 1 == 0.0) {

                binding.resultTV.text = total.toInt().toString()

            } else {

                binding.resultTV.text = String.format(Locale.US, "%3f", total)

            }

            expression = total.toString()

        }

        binding.AC.setOnClickListener {
            canAddDecimal = true

            resultTV.text = ""
            expression = ""

        }


    }


}





