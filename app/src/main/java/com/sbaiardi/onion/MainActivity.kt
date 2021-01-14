package com.sbaiardi.onion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.onesignal.OneSignal
import com.sbaiardi.onion.data.api.ApiHelper
import com.sbaiardi.onion.data.api.RetrofitBuilder

import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModel
import com.sbaiardi.onion.data.roomViewModel.PercentageViewModelFactory
import com.sbaiardi.onion.ui.main.base.ViewModelFactory
import com.sbaiardi.onion.ui.main.view.MainFragment
import com.sbaiardi.onion.ui.main.viewmodel.MainViewModel
import com.sbaiardi.onion.utils.Status
import kotlinx.android.synthetic.main.main_activity.*


const val ONESIGNAL_APP_ID = "8039cf0f-9a09-4537-8763-23f9c007a446"

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()

        navigationView.setItemSelected(R.id.nav_home)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)


        //setupViewModel()
        //setupObservers()
    }





}