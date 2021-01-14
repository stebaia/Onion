package com.sbaiardi.onion.data.api

import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("api/covid/positive_percentage")
    suspend fun getPositivePercentage(): Response


    @GET("api/covid/get_last_positive_percentage")
    suspend fun getLastPositivePercentage(@Query("date") data: String): Response

}