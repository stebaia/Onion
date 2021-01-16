package com.sbaiardi.onion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.onesignal.OneSignal
import com.sbaiardi.onion.data.api.ApiHelper
import com.sbaiardi.onion.data.api.RetrofitBuilder
import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModel
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModelFactory
import com.sbaiardi.onion.ui.main.base.ViewModelFactory
import com.sbaiardi.onion.ui.main.viewmodel.MainViewModel
import com.sbaiardi.onion.utils.Status
import kotlinx.android.synthetic.main.import_data_activity_layout.*
import kotlinx.coroutines.launch

const val ONESIGNAL_APP_ID = "8039cf0f-9a09-4537-8763-23f9c007a446"

class ImportDataActivity: AppCompatActivity() {

    private val percentageViewModel: PercentageViewModel by viewModels {
        PercentageViewModelFactory((application as OnionApplication).repository)
    }

    private val PREFS_NAME = "MyPrefsFile"


    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.import_data_activity_layout)
        if (savedInstanceState == null) {

        }

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        setupViewModel()



        val settings = getSharedPreferences(PREFS_NAME, 0)

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time")

                    //controllo se ci sono già elementi esistenti
            Log.d("LIST_PERCENTAGES", "Ok: ci sono elementi")
                    //se ci sono elementi faccio la chiamata per tirarmi giù gli ultimi e inserirli
            importLastElements("2020-02-23T18:00:00",true)
            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit()
        }else{
            percentageViewModel.allPer.observe(owner = this){per ->
                per.let {
                    //controllo se ci sono già elementi esistenti
                    Log.d("LIST_PERCENTAGES", "Ok: ci sono elementi")
                    //se ci sono elementi faccio la chiamata per tirarmi giù gli ultimi e inserirli
                    importLastElements(per[per.lastIndex].data,false)
                }
            }
        }


        }


    private fun importLastElements(data: String, isFirst: Boolean){

        viewModel.getLastPositivePercentages(data).observe(this, Observer {
            it?.let {
                    resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                            /*for (item in resource.data?.percentages!!){
                                val percentage = Percentages(item.data, item.n_positivi, item.n_tamponi,item.positive_percentage)
                                percentageViewModel.insert(percentage)
                            }*/
                        percentageViewModel.insertAll(resource.data?.percentages !! as ArrayList<Percentages>)
                        if (isFirst) {
                            Handler().postDelayed({
                                startActivity(
                                    Intent(
                                        this@ImportDataActivity,
                                        ImportDataActivity::class.java
                                    )
                                )
                                finish()
                            }, 8000)
                        }else {
                            startActivity(
                                Intent(
                                    this@ImportDataActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
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