package com.sbaiardi.onion.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sbaiardi.onion.data.model.Percentages
import kotlinx.coroutines.flow.Flow


@Dao
interface PercentageDao {

    @Query("SELECT * FROM percentages")
    fun getAllPercentages() : Flow<List<Percentages>>

    @Query("SELECT * FROM percentages ORDER BY data DESC LIMIT 7")
    fun getLastWeekPercentage() : Flow<List<Percentages>>

    @Query("SELECT * FROM percentages ORDER BY data DESC LIMIT 30")
    fun getLastMonthPercentage() : Flow<List<Percentages>>

    @Query("SELECT * FROM percentages WHERE data =:data")
    fun getPercentage(data: String?): LiveData<Percentages>

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //suspend fun insertAll(percentageTableModel: List<Percentages>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(percentageTableModel: Percentages)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(percentages: ArrayList<Percentages>)

    @Query("DELETE FROM percentages")
    suspend fun deleteAll()
}

