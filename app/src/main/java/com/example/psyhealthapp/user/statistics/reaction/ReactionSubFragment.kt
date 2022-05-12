package com.example.psyhealthapp.user.statistics.reaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.testing.results.ReactionTestResultList
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.lang.Float
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.StatReactionSubfragmentBinding
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class ReactionSubFragment : Fragment(R.layout.stat_reaction_subfragment) {
    private val viewBinding by viewBinding(StatReactionSubfragmentBinding::bind)

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")

        private const val TEST_RESULT = "testResult"
        private const val VALUE_TEXT_SIZE = 14F
        private const val DASHED_LINE_LENGTH = 10F
        private const val XAXIS_PERCENT = 5F
        private const val POINTS_AT_SCREEN = 5F
        private const val DATA_SET_LINE_WIDTH = 2F
        private const val XAXIS_GRANULARITY = 1F

        private const val YAXIS_MAX_SCALE_COEF = 1.2F
        private const val YAXIS_MIN_SCALE_COEF = 0.8F

        fun newInstance(reactionResult: ReactionTestResultList): ReactionSubFragment {
            val arguments = Bundle()
            val reaction = ReactionSubFragment()
            arguments.putParcelable(TEST_RESULT, reactionResult)
            reaction.arguments = arguments
            return reaction
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("args is ${arguments}")
        arguments?.let {
            val testResultList = it.getParcelable<ReactionTestResultList>(TEST_RESULT)
            testResultList?.let {
                setupView(view, testResultList)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setupView(view: View, testResultList: ReactionTestResultList) {
        setupChart(testResultList)

        val bestResult = testResultList.results.maxByOrNull { it.result }?.result?.roundToInt() ?: 0
        val averageResult = testResultList.results.map { it.result }.average().roundToInt()

        viewBinding.bestReactionResult.text = " $bestResult"
        viewBinding.averageReactionResult.text = " $averageResult"
    }

    private fun setupChart(testResultList: ReactionTestResultList) {
        val chart = viewBinding.chart
        val results =
            testResultList.results.mapIndexed { it, i -> Entry(it.toFloat() + 1, i.result) }
                .toMutableList()

        val maxResult = testResultList.results.maxByOrNull { it.result }!!.result
        val minResult = testResultList.results.minByOrNull { it.result }!!.result

        results.add(0, Entry(0F, minResult - 1))
        results.add(Entry(results.size.toFloat(), minResult - 1))

        val dataSet = LineDataSet(results, "reaction_attempts")

        dataSet.apply {
            lineWidth = DATA_SET_LINE_WIDTH
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = valueTextSize
            color = ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_3
            )
            fillColor = ContextCompat.getColor(
                requireContext(), R.color.stat_tests_tapping_textColor_1
            )
            valueTextColor = ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_tapping_chart_lineColor_3
            )
            valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.oswald)
            enableDashedLine(DASHED_LINE_LENGTH, DASHED_LINE_LENGTH, DASHED_LINE_LENGTH)

            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: kotlin.Float): String {
                    return if (value == minResult - 1) {
                        ""
                    } else {
                        value.toInt().toString()
                    }
                }
            }

            setDrawFilled(true)
            setDrawCircles(false)
            setDrawValues(true)
        }

        val dates = testResultList.results.map { it.date.format(formatter) }

        chart.xAxis.apply {
            granularity = XAXIS_GRANULARITY
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: kotlin.Float): String {
                    val id = value.roundToInt() - 1
                    return if (id in 0 until testResultList.results.size) {
                        dates[id]
                    } else {
                        ""
                    }
                }
            }
            setDrawGridLines(false)
        }

        chart.axisLeft.apply {
            axisMaximum = maxResult * YAXIS_MAX_SCALE_COEF
            axisMinimum = minResult * YAXIS_MIN_SCALE_COEF
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        chart.apply {
            setScaleMinima(
                Float.max(
                    POINTS_AT_SCREEN,
                    testResultList.results.size.toFloat()
                ) / POINTS_AT_SCREEN, 1f
            )
            centerViewTo(
                Float.max(
                    0F,
                    testResultList.results.size - POINTS_AT_SCREEN
                ), 0F, YAxis.AxisDependency.LEFT
            )
            data = LineData(dataSet)

            axisRight.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false

            animateY(250)
            invalidate()
        }
    }
}