package com.sbaiardi.onion.data.roomRepository

import androidx.annotation.WorkerThread
import com.sbaiardi.onion.data.dao.PercentageDao
import com.sbaiardi.onion.data.model.Percentages
import kotlinx.coroutines.flow.Flow


class PercentagesRepo(private val perDao: PercentageDao) {

    val allPercentages : Flow<List<Percentages>> = perDao.getAllPercentages()

    val filterWeekPercentages : Flow<List<Percentages>> = perDao.getLastWeekPercentage()

    val filterMonthPercentages : Flow<List<Percentages>> = perDao.getLastMonthPercentage()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(per: Percentages) {
        perDao.insertData(per)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(pers: ArrayList<Percentages>) {
        perDao.insertAll(pers)
    }


}