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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, ImportDataActivity::class.java))
            finish()
        }, 2000)




    }



}