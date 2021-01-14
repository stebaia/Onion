package com.sbaiardi.onion.data.roomRepository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sbaiardi.onion.data.dao.PercentageDao
import com.sbaiardi.onion.data.model.Percentages
import kotlinx.coroutines.flow.Flow


class PercentagesRepo(private val perDao: PercentageDao) {

    val allPercentages : Flow<List<Percentages>> = perDao.getAllPercentages()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(per: Percentages) {
        perDao.insertData(per)
    }

}