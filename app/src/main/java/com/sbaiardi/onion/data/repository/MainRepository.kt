package com.sbaiardi.onion.data.repository

import com.sbaiardi.onion.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getPositivePercentage() = apiHelper.getPositivePercentage()

    suspend fun getLastPositivePercentage(data: String) = apiHelper.getLastPositivePercentage(data)


}