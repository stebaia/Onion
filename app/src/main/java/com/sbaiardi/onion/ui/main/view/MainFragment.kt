package com.sbaiardi.onion.ui.main.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.db.williamchart.ExperimentalFeature
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sbaiardi.onion.OnionApplication
import com.sbaiardi.onion.R
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModel
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModelFactory
import com.sbaiardi.onion.utils.AnimatorUtils.Companion.startCountAnimationDouble
import com.sbaiardi.onion.utils.AnimatorUtils.Companion.startCountAnimationInt
import com.sbaiardi.onion.utils.DateFormatUtils.Companion.dateFormat
import com.sbaiardi.onion.utils.LottieAnimationUtils.Companion.loadLottieAnim
import com.sbaiardi.onion.utils.LottieAnimationUtils.Companion.loadLottieAnimDouble
import com.sbaiardi.onion.utils.chart.ChartUtils.Companion.setLineChart
import com.sbaiardi.onion.utils.dialog.*
import kotlinx.android.synthetic.main.custom_dialog_layout.*
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val percentageViewModel: PercentageViewModel by viewModels {
        PercentageViewModelFactory((activity?.application as OnionApplication).repository)
    }

    companion object {
        fun newInstance() = MainFragment()
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
        loadAllPercentage()
        btn_month.setOnClickListener {
            loadMonthPercentage()
            btn_month.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_colored)
            btn_week.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
            btn_all.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
        }
        btn_all.setOnClickListener {
            loadAllPercentage()
            btn_month.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
            btn_week.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
            btn_all.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_colored)
        }
        btn_week.setOnClickListener {
            loadWeekPercentage()
            btn_month.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
            btn_week.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_colored)
            btn_all.background = ContextCompat.getDrawable(this!!.requireActivity(),R.drawable.button_rounded_uncolored)
        }
    }

    //TODO: da parametrizzare il metodo Load, perchè ora sono tre molto simili

    private fun loadMonthPercentage() {
        percentageViewModel.filterMonth.observe(owner = viewLifecycleOwner) { per ->
            per.reversed().let {
                val iter = it
                if (iter.isNotEmpty()) {
                    startCountAnimationInt(txt_positive, iter.last().n_positivi)
                    startCountAnimationInt(txt_tamponi, iter.last().n_tamponi)
                    startCountAnimationDouble(
                        txt_positive_percentage,
                        iter.last().positive_percentage.toFloat()
                    )

                    setLineChart(1,iter, lineChart,txt_type_graph, context)

                    loadLottieAnim(
                        iter.last().n_positivi,
                        iter[iter.lastIndex - 1].n_positivi,
                        animationViewUpPositive,
                        animationViewDownPositive
                    )
                    loadLottieAnim(
                        iter.last().n_tamponi,
                        iter[iter.lastIndex - 1].n_tamponi,
                        animationViewUpTamponi,
                        animationViewDownTamponi
                    )
                    loadLottieAnimDouble(
                        iter.last().positive_percentage,
                        iter[iter.lastIndex - 1].positive_percentage,
                        animationViewUpPercentage,
                        animationViewDownPercentage
                    )

                    txt_aggiornamento_dati.text = "dati aggiornati alle 17 del " + dateFormat(iter.last().data)

                    img_graph_choise.setOnClickListener(View.OnClickListener {
                        CustomDialog.build(activity)
                            .title("Che dati vuoi vedere?",null, Color.BLACK)
                            .onFirstChoise("Percentuale",R.drawable.button_rounded_colored, Color.WHITE){
                                Log.d("TAG", "Percentuale")
                                setLineChart(1,iter, lineChart,txt_type_graph, context)
                            }
                            .onSecondChoise("Positivi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Positivi")
                                setLineChart(2,iter, lineChart,txt_type_graph, context)
                            }
                            .onThirdChoise("Tamponi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Tamponi")
                                setLineChart(3,iter, lineChart,txt_type_graph, context)
                            }
                            .close()
                    })
                }
            }
        }
    }

    private fun loadWeekPercentage() {
        percentageViewModel.filterWeek.observe(owner = viewLifecycleOwner) { per ->
            per.reversed().let {
                val iter = it
                if (iter.isNotEmpty()) {
                    startCountAnimationInt(txt_positive, iter.last().n_positivi)
                    startCountAnimationInt(txt_tamponi, iter.last().n_tamponi)
                    startCountAnimationDouble(
                        txt_positive_percentage,
                        iter.last().positive_percentage.toFloat()
                    )

                    setLineChart(1,iter, lineChart,txt_type_graph, context)

                    loadLottieAnim(
                        iter.last().n_positivi,
                        iter[iter.lastIndex - 1].n_positivi,
                        animationViewUpPositive,
                        animationViewDownPositive
                    )
                    loadLottieAnim(
                        iter.last().n_tamponi,
                        iter[iter.lastIndex - 1].n_tamponi,
                        animationViewUpTamponi,
                        animationViewDownTamponi
                    )
                    loadLottieAnimDouble(
                        iter.last().positive_percentage,
                        iter[iter.lastIndex - 1].positive_percentage,
                        animationViewUpPercentage,
                        animationViewDownPercentage
                    )

                    txt_aggiornamento_dati.text = "dati aggiornati alle 17 del " + dateFormat(iter.last().data)

                    img_graph_choise.setOnClickListener(View.OnClickListener {
                        CustomDialog.build(activity)
                            .title("Che dati vuoi vedere?",null, Color.BLACK)
                            .onFirstChoise("Percentuale",R.drawable.button_rounded_colored, Color.WHITE){
                                Log.d("TAG", "Percentuale")
                                setLineChart(1,iter, lineChart,txt_type_graph, context)
                            }
                            .onSecondChoise("Positivi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Positivi")
                                setLineChart(2,iter, lineChart,txt_type_graph, context)
                            }
                            .onThirdChoise("Tamponi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Tamponi")
                                setLineChart(3,iter, lineChart,txt_type_graph, context)
                            }
                            .close()
                    })
                }
            }
        }
    }

    private fun loadAllPercentage() {
        percentageViewModel.allPer.observe(owner = viewLifecycleOwner) { per ->
            per.let {
                if (per.isNotEmpty()) {
                    startCountAnimationInt(txt_positive, per.last().n_positivi)
                    startCountAnimationInt(txt_tamponi, per.last().n_tamponi)
                    startCountAnimationDouble(
                        txt_positive_percentage,
                        per.last().positive_percentage.toFloat()
                    )

                    setLineChart(1,per, lineChart,txt_type_graph, context)

                    loadLottieAnim(
                        per.last().n_positivi,
                        per[per.lastIndex - 1].n_positivi,
                        animationViewUpPositive,
                        animationViewDownPositive
                    )
                    loadLottieAnim(
                        per.last().n_tamponi,
                        per[per.lastIndex - 1].n_tamponi,
                        animationViewUpTamponi,
                        animationViewDownTamponi
                    )
                    loadLottieAnimDouble(
                        per.last().positive_percentage,
                        per[per.lastIndex - 1].positive_percentage,
                        animationViewUpPercentage,
                        animationViewDownPercentage
                    )

                    txt_aggiornamento_dati.text = "dati aggiornati alle 17 del " + dateFormat(per.last().data)

                    img_graph_choise.setOnClickListener(View.OnClickListener {
                        CustomDialog.build(activity)
                            .title("Che dati vuoi vedere?",null, Color.BLACK)
                            .onFirstChoise("Percentuale",R.drawable.button_rounded_colored, Color.WHITE){
                                Log.d("TAG", "Percentuale")
                                setLineChart(1,per, lineChart,txt_type_graph, context)
                            }
                            .onSecondChoise("Positivi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Positivi")
                                setLineChart(2,per, lineChart,txt_type_graph, context)
                            }
                            .onThirdChoise("Tamponi",R.drawable.button_rounded_colored,Color.WHITE){
                                Log.d("TAG", "Tamponi")
                                setLineChart(3,per, lineChart,txt_type_graph, context)
                            }
                            .close()
                    })
                }
            }
        }
    }

}


