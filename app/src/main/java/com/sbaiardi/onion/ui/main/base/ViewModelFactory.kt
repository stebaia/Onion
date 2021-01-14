package com.sbaiardi.onion.ui.main.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbaiardi.onion.data.api.ApiHelper
import com.sbaiardi.onion.data.repository.MainRepository
import com.sbaiardi.onion.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private  val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}