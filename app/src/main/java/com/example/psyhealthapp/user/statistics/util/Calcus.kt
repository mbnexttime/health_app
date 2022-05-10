package com.example.psyhealthapp.user.statistics.util

class Calcus {
    companion object {
        fun getDerivate(values: MutableList<Pair<Float, Float>>): List<Pair<Float, Float>> {
            if (values.first().first != 0F) {
                values.add(0, Pair(0F, values.first().second))
            }

            val derivate = mutableListOf<Pair<Float, Float>>()
            for (i in 0 until values.size - 1) {
                derivate.add(
                    Pair(
                        values[i].first,
                        (values[i + 1].second - values[i].second) / (values[i + 1].first - values[i].first)
                    )
                )
            }

            return derivate
        }
    }

}