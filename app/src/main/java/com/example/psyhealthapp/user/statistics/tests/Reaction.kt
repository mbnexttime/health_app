package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ReactionResult(val days: List<String>, val values: List<Float>) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(days)
        parcel.writeFloatArray(values.toFloatArray())
    }

    companion object CREATOR : Parcelable.Creator<ReactionResult> {
        override fun createFromParcel(parcel: Parcel): ReactionResult {
            val days = parcel.createStringArray()!!.toList()
            val values = parcel.createFloatArray()!!.toList()
            return ReactionResult(days, values)
        }

        override fun newArray(size: Int): Array<ReactionResult?> {
            return arrayOfNulls(size)
        }
    }
}

class Reaction : Fragment(R.layout.stat_tests_reaction) {
    private lateinit var chart: LineChart

    companion object {
        enum class ChartConstants(val value: Float) {
            VALUE_TEXT_SIZE(14F),
            DASHED_LINE_LENGTH(10F)
        }

        fun newInstance(reactionResult: ReactionResult): Reaction {
            val arguments = Bundle()
            val reaction = Reaction()
            arguments.putParcelable("testResult", reactionResult)
            reaction.arguments = arguments
            return reaction
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)
        arguments?.let {
            val testResult = it.getParcelable<ReactionResult>("testResult")
            testResult?.let {
                setupChart(testResult)
            }
        }
    }

    private fun setupChart(testResult: ReactionResult) {
        val dataSet =
            LineDataSet(
                testResult.values.mapIndexed { it, i -> Entry(it.toFloat(), i) },
                "reaction_attempts"
            )

        dataSet.apply {
            lineWidth = 2F
            setDrawCircles(false)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = valueTextSize
            color =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_3
                )
            setDrawFilled(true)
            fillColor =
                ContextCompat.getColor(requireContext(), R.color.stat_tests_tapping_textColor_1)
            valueTextColor =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
            valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.oswald)
            enableDashedLine(
                ChartConstants.DASHED_LINE_LENGTH.value,
                ChartConstants.DASHED_LINE_LENGTH.value,
                ChartConstants.DASHED_LINE_LENGTH.value
            )
        }

        chart.apply {
            setTouchEnabled(false)
            description.isEnabled = false
            data = LineData(dataSet)
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            legend.isEnabled = false
            animateY(250)
            invalidate()
        }
    }
}