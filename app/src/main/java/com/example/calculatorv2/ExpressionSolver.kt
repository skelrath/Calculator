package com.example.calculatorv2


class ExpressionSolver {

    companion object {

        fun eval(expression: String): Double { //функция для решения выражения

            val numberStrings: MutableList<String> = mutableListOf() //лист стринговых чисел
            val characters: MutableList<String> = mutableListOf() //лист знаков
            var numberIndex = -1 //число для навигации по листу чисел
            var canAddNumberToNumberInList = false /* можно ли добавить к стринговому числу
            еще одно число, либо же добавить новую ячейку в лист */

            for (i in expression) {

                if (i == '.') {

                    numberStrings[numberStrings.lastIndex] += "."
                    continue

                }

                if (i.isDigit()) {

                    if (canAddNumberToNumberInList) {

                        numberStrings[numberIndex] += i.toString() //добавляем число к числу -> '1' + '2' = '12'

                    } else {

                        numberStrings.add(i.toString()) //создаем новую ячейку в списку для следующего числа

                        numberIndex++
                        canAddNumberToNumberInList = true

                    }

                    continue
                }

                canAddNumberToNumberInList =
                    false //меняем на false так как теперь нам нужно проиницилизировать следующее число

                when {

                    i.toString() == "%" -> { //если i процент, то просто умножаем последнюю ячейку в листе числе на 0.01

                        numberStrings[numberStrings.lastIndex] =
                            (numberStrings[numberIndex].toInt() * 0.01).toString()


                    }

                    else -> {

                        characters.add(i.toString()) //просто добавляем знак

                    }

                }


            }

            val numbers: MutableList<Double> =
                numberStrings.map { it.toDouble() } as MutableList<Double> //переводим список стринговых чисел в обычные числа

            var character: String
            var total: Double = numbers[0]

            numbers.removeAt(0)


            for ((characterIndex, number) in numbers.withIndex()) { //проходимся по листу чисел
                character = characters[characterIndex]

                when (character) {
                    "+" -> total += number
                    "-" -> total -= number
                    "*" -> total *= number
                    else -> {
                        total /= number
                    }
                }

            }


            return total


        }
    }
}