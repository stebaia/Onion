package com.sbaiardi.onion.utils.chart.marker

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.highlight.Highlight


class CustomMarkerView(ctx: Context?, resource: Int) : MarkerView(ctx, resource) {
    private var tvContent: TextView? = null




    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    fun refreshContent(
        e: Map.Entry<*, *>,
        highlight: Highlight?
    ) {
        tvContent!!.text = "" + e.value // set the entry-value as the display text
    }

    fun getXOffset(): Int {
        // this will center the marker-view horizontally
        return -(width / 2)
    }

    fun getYOffset(): Int {
        // this will cause the marker-view to be above the selected value
        return -height
    }
}