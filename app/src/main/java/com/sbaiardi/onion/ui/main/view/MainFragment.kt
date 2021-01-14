package com.sbaiardi.onion.ui.main.view

import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.observe
import com.airbnb.lottie.LottieAnimationView
import com.db.williamchart.ExperimentalFeature
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sbaiardi.onion.OnionApplication
import com.sbaiardi.onion.R
import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModel
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModelFactory
import com.sbaiardi.onion.utils.charts.markers.CustomMarkerView
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val percentageViewModel: PercentageViewModel by viewModels {
        PercentageViewModelFactory((activity?.application as OnionApplication).repository)
    }
    var date = "14/01/2021"
    companion object {
        fun newInstance() = MainFragment()
        private val lineSet = listOf(
            "Giovedi" to 9.5f,
            "Venerdi" to 12.6f,
            "Sabato" to 14.1f,
            "Domenica" to 17.8f,
            "Giovedi" to 9.5f,
            "Venerdi" to 12.6f,
            "Sabato" to 14.1f,
            "Domenica" to 17.8f,
            "Lunedi" to 13.8f
        )

        private val barSet = listOf(
            "JAN" to 4F,
            "FEB" to 7F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "JUN" to 4F
        )

        private val horizontalBarSet = listOf(
            "PORRO" to 5F,
            "FUSCE" to 6.4F,
            "EGET" to 3F
        )

        private val donutSet = listOf(
            20f,
            80f,
            100f
        )

        private const val animationDuration = 1000L
    }

    //private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @OptIn(ExperimentalFeature::class)
    override fun onViewCreated(view: View, saveInstanceState: Bundle?) {
        percentageViewModel.allPer.observe(owner = viewLifecycleOwner){per ->
            per.let {
                //txt_positive.text = String.format("%,2d",per.last().n_positivi)
                //txt_positive_percentage.text = String.format("%.2f",per.last().positive_percentage) +"%"
                //txt_tamponi.text = String.format("%,2d",per.last().n_tamponi)
                startCountAnimationInt(txt_positive,per.last().n_positivi)
                startCountAnimationInt(txt_tamponi,per.last().n_tamponi)
                startCountAnimationDouble(txt_positive_percentage,per.last().positive_percentage.toFloat())
                setLineChart(per)
                loadLottieAnim(per.last().n_positivi, per[per.lastIndex-1].n_positivi, animationViewUpPositive,animationViewDownPositive)
                loadLottieAnim(per.last().n_tamponi, per[per.lastIndex-1].n_tamponi, animationViewUpTamponi,animationViewDownTamponi)
                loadLottieAnimDouble(per.last().positive_percentage, per[per.lastIndex-1].positive_percentage, animationViewUpPercentage,animationViewDownPercentage)

                txt_aggiornamento_dati.text = "dati aggiornati alle 17 del "+dateFormat(per.last().data)
            }
        }

    }

    private fun dateFormat(dateToFormat: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = inputFormat.parse(dateToFormat)
        return outputFormat.format(date)
    }


    private fun startCountAnimationInt(textView: TextView, value: Int ) {
        val animator = ValueAnimator.ofInt(0, value)
        animator.duration = 2500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation -> textView.text = (String.format("%,2d",animation.animatedValue)) }
        animator.start()
    }

    private fun startCountAnimationDouble(textView: TextView, value: Float ) {
        val animator = ValueAnimator.ofFloat(0f, value)
        animator.duration = 2500
        animator.interpolator = FastOutSlowInInterpolator()
        animator.addUpdateListener { animation -> textView.text = (String.format("%.2f",animation.animatedValue)) }
        animator.start()
    }

    private fun loadLottieAnim(perLast: Int, perBeforeLast: Int, up: LottieAnimationView, down: LottieAnimationView){
        if (perLast > perBeforeLast) {
            up.visibility = View.VISIBLE
            down.visibility = View.GONE
        }
        else {
            up.visibility = View.GONE
            down.visibility = View.VISIBLE
        }
    }
    private fun loadLottieAnimDouble(perLast: Double, perBeforeLast: Double, up: LottieAnimationView, down: LottieAnimationView){
        if (perLast > perBeforeLast) {
            up.visibility = View.VISIBLE
            down.visibility = View.GONE
        }
        else {
            up.visibility = View.GONE
            down.visibility = View.VISIBLE
        }
    }

    private fun setLineChart(percentages: List<Percentages>) {

        /*val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))*/

        val entries = ArrayList<Entry>()
        for ((index,percentage) in percentages.withIndex()){

            entries.add(Entry(index.toFloat(),percentage.positive_percentage.toFloat()))
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
            1000,1000,
            com.github.mikephil.charting.animation.Easing.Linear
        )// set the description+
        var customMarkerView = CustomMarkerView(context,R.layout.custom_maker_view_layout)
        lineChart.marker = customMarkerView


        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
    }



}