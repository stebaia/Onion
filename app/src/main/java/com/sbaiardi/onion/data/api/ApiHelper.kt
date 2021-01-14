package com.sbaiardi.onion.data.api

class ApiHelper(private val  apiService: ApiService) {
    suspend fun getPositivePercentage() = apiService.getPositivePercentage()

    suspend fun getLastPositivePercentage(data: String) = apiService.getLastPositivePercentage(data)

}