package com.example.calculatorv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true
    private var expressionToSolve = ""
    private var lastNumber = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun allClearAction(view: View) {
        resultTV.text = ""
        expressionToSolve = ""
    }


    fun numberAction(view: View) {
        if (view is Button && canAddDecimal) {
            if (view.text == ",") {
                if (canAddDecimal) {
                    resultTV.append(view.text)
                    expressionToSolve += view.text
                }
                canAddDecimal = false
            } else {
                if (canAddDecimal && canAddOperation) {
                    resultTV.append(view.text)
                    expressionToSolve += view.text
                } else {
                    resultTV.text = view.text
                    expressionToSolve += view.text
                }
                lastNumber = view.text as String
            }

            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            resultTV.text = view.text
            expressionToSolve += view.text
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun equalAction(view: View) {
        val solvedExpression = solveExpression(expressionToSolve)
        resultTV.text = solvedExpression
        expressionToSolve = solvedExpression
    }


    private fun solveExpression(expression: String): String {
        return Solution.eval(expression).toString().format(2)
    }

    fun plusMinusAction(view: View) {
        expressionToSolve += "*-1"
    }


}


