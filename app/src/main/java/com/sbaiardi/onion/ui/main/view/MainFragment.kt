package com.sbaiardi.onion.ui.main.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
        img_graph_choise.setOnClickListener(View.OnClickListener {
            CustomDialog.build(activity)
                .title("Che dati vuoi vedere?",null, Color.BLACK)
                .onFirstChoise("Percentuale",R.drawable.button_rounded_colored, Color.WHITE){
                    Log.d("TAG", "Percentuale")
                }
                .onSecondChoise("Positivi",R.drawable.button_rounded_colored,Color.WHITE){
                    Log.d("TAG", "Positivi")
                }
                .onThirdChoise("Tamponi",R.drawable.button_rounded_colored,Color.WHITE){
                    Log.d("TAG", "Tamponi")
                }
        })
        percentageViewModel.allPer.observe(owner = viewLifecycleOwner) { per ->
            per.let {
                if (per.isNotEmpty()) {
                    startCountAnimationInt(txt_positive, per.last().n_positivi)
                    startCountAnimationInt(txt_tamponi, per.last().n_tamponi)
                    startCountAnimationDouble(
                        txt_positive_percentage,
                        per.last().positive_percentage.toFloat()
                    )

                    setLineChart(per, lineChart, context)

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

                    txt_aggiornamento_dati.text =
                        "dati aggiornati alle 17 del " + dateFormat(per.last().data)
                }
            }
        }

    }

}


