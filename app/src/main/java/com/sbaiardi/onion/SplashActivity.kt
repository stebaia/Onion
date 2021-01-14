package com.sbaiardi.onion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.sbaiardi.onion.data.api.ApiHelper
import com.sbaiardi.onion.data.api.RetrofitBuilder
import com.sbaiardi.onion.data.dao.PercentageDao
import com.sbaiardi.onion.data.db.AppDatabase
import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModel
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModelFactory
import com.sbaiardi.onion.ui.main.base.ViewModelFactory
import com.sbaiardi.onion.ui.main.view.MainFragment
import com.sbaiardi.onion.ui.main.viewmodel.MainViewModel
import com.sbaiardi.onion.utils.Status

class SplashActivity : AppCompatActivity() {

    private val percentageViewModel: PercentageViewModel by viewModels {
        PercentageViewModelFactory((application as OnionApplication).repository)
    }


    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setupViewModel()
        percentageViewModel.allPer.observe(owner = this){per ->
            per.let {
                //controllo se ci sono già elementi esistenti
                if  (per.size > 1) {
                    val percentages = per
                    Log.d("LIST_PERCENTAGES", "Ok: ci sono elementi")
                    //se ci sono elementi faccio la chiamata per tirarmi giù gli ultimi e inserirli
                    importLastElements(per[per.lastIndex].data)
                }else{
                    Log.d("LIST_PERCENTAGES", "Non ci sono elementi salvati")
                    //init dei dati
                    initPercentages()
                }

            }
        }
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }

    private fun importLastElements(data: String){
        viewModel.getLastPositivePercentages(data).observe(this, Observer {
            it?.let {
                resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        for (item in resource.data?.percentages!!){
                            val percentage = Percentages(item.data, item.n_positivi, item.n_tamponi,item.positive_percentage)
                            percentageViewModel.insert(percentage)
                        }
                        Log.d("Status api","SUCCESS: get positive percentage")
                    }
                    Status.ERROR -> {
                        Log.d("Status api","Error: not get positive percentage")
                    }
                    Status.LOADING -> {
                        Log.d("Status api","Loading api")
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun initPercentages() {

        viewModel.getPositivePercentage().observe(this, Observer {
            it?.let {
                    resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        for (item in resource.data?.percentages!!){
                            val percentage = Percentages(item.data, item.n_positivi, item.n_tamponi,item.positive_percentage)
                            percentageViewModel.insert(percentage)
                        }
                        Log.d("Status api","SUCCESS: get positive percentage")
                    }
                    Status.ERROR -> {
                        Log.d("Status api","Error: not get positive percentage")
                    }
                    Status.LOADING -> {
                        Log.d("Status api","Loading api")
                    }
                }
            }
        })
    }

}