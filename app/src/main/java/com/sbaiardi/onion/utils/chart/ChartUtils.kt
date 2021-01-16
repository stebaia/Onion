package com.sbaiardi.onion.utils.chart

import android.content.Context
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.sbaiardi.onion.R
import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.utils.chart.marker.CustomMarkerView

class ChartUtils {

    companion object {
        public fun setLineChart(
            percentages: List<Percentages>,
            lineChart: LineChart,
            context: Context?
        ) {

            /*val entries = ArrayList<Entry>()
            entries.add(Entry(1f, 10f))
            entries.add(Entry(2f, 2f))
            entries.add(Entry(3f, 7f))
            entries.add(Entry(4f, 20f))
            entries.add(Entry(5f, 16f))*/

            val entries = ArrayList<Entry>()
            for ((index, percentage) in percentages.withIndex()) {

                entries.add(Entry(index.toFloat(), percentage.positive_percentage.toFloat()))
            }


            val lineDataSet = LineDataSet(entries, "Cells")
            lineDataSet.setDrawValues(false)
            lineDataSet.lineWidth = 3f
            lineDataSet.valueTextSize = 7f
            lineDataSet.setCircleColor(R.color.violaScuro)
            lineDataSet.circleHoleColor = R.color.colorPrimary
            lineDataSet.color = R.color.colorPrimary

            val data = LineData(lineDataSet)
            lineChart.xAxis.labelRotationAngle = 0f

            lineChart.data = data // set the data and list of lables into chart

            lineChart.axisRight.isEnabled = false
            lineChart.setTouchEnabled(true)
            lineChart.setPinchZoom(true)
            lineChart.animateXY(
                1000, 1000,
                com.github.mikephil.charting.animation.Easing.Linear
            )// set the description+
            var customMarkerView =
                CustomMarkerView(
                    context,
                    R.layout.custom_maker_view_layout
                )
            lineChart.marker = customMarkerView


        }

    }
}