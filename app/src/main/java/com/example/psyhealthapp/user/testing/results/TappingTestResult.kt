package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import com.example.psyhealthapp.user.testing.results.TappingTestResult.CREATOR.NERVOUS_SYSTEM_TYPES
import java.time.LocalDate
import java.util.*
import kotlin.math.abs

class TappingTestResult(
    date: LocalDate,
    val rightHandMoments: List<Float>,
    val leftHandMoments: List<Float>,
) : Parcelable, TestResult(date) {

    data class ClearlyResults(
        val rHandGraph: List<Pair<Float, Float>>,
        val lHandGraph: List<Pair<Float, Float>>,
        val nervousSystemType: String,
        val nervousSystemPowerRatio: Float,
        val asymmetry: Float
    )

    fun getClearlyResult(): ClearlyResults {
        val rHandGraph = getGraph(rightHandMoments)
        val lHandGraph = getGraph(leftHandMoments)

        val rSum = rHandGraph.map { it.second }.sum()
        val lSum = lHandGraph.map { it.second }.sum()

        val asymmetry = abs(rSum - lSum) / (rSum + lSum) * 100F
        val nervousSystemPowerRatio = (getRatio(lHandGraph) + getRatio(rHandGraph)) / 2
        val nervousSystemType = ((getType(lHandGraph) + getType(rHandGraph)) / 2).toInt()

        return ClearlyResults(
            rHandGraph,
            lHandGraph,
            NERVOUS_SYSTEM_TYPES[nervousSystemType],
            nervousSystemPowerRatio,
            asymmetry
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        date.writeToParcel(parcel)
        parcel.writeFloatArray(rightHandMoments.toFloatArray())
        parcel.writeFloatArray(leftHandMoments.toFloatArray())
    }

    companion object CREATOR : Parcelable.Creator<TappingTestResult> {
        private const val RESULT_SIZE = 6
        private const val TEST_DURATION = 10F

        private val NERVOUS_SYSTEM_TYPES = listOf("слабая", "средне-слабая", "средняя", "сильная")

        private const val WEAK = 0
        private const val MEDIUM_WEAK = 1
        private const val MEDIUM = 2
        private const val STRONG = 3

        override fun createFromParcel(parcel: Parcel): TappingTestResult {
            val date = getLocalDateFromParcel(parcel)
            val rightHandMoments = parcel.createFloatArray()!!.toList()
            val leftHandMoments = parcel.createFloatArray()!!.toList()
            return TappingTestResult(date, rightHandMoments, leftHandMoments)
        }

        override fun newArray(size: Int): Array<TappingTestResult?> {
            return arrayOfNulls(size)
        }

        fun getGraph(values: List<Float>): List<Pair<Float, Float>> {
            val delta = TEST_DURATION / RESULT_SIZE
            return (0 until RESULT_SIZE).map { i ->
                val l = i * delta
                val r = (i + 1) * delta
                values.count { elem -> elem in l..r }
            }.mapIndexed { it, i ->
                Pair(it.toFloat(), i.toFloat())
            }
        }

        fun getRatio(graph: List<Pair<Float, Float>>): Float {
            return 100F * (graph.map { it.second }
                .sum() - graph[0].second * graph.size) / graph[0].second
        }

        fun getType(graph: List<Pair<Float, Float>>): Int {
            val values = graph.map { it.second }
            val firstValue = values.first()
            val maxValue = values.maxOrNull()!!
            val maxIndexLast = values.lastIndexOf(maxValue)

            if (maxIndexLast == 0) {
                return WEAK
            }
            if (values[1] > firstValue) {
                if (values[2] >= firstValue && values[3] >= firstValue)
                    return STRONG
                return if (values[2] < firstValue) {
                    MEDIUM_WEAK
                } else {
                    MEDIUM
                }
            }

            return MEDIUM_WEAK
        }
    }
}