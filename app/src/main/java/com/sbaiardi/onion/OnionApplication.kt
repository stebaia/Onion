package com.sbaiardi.onion

import android.app.Application
import com.sbaiardi.onion.data.db.AppDatabase
import com.sbaiardi.onion.data.roomRepository.PercentagesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class OnionApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { PercentagesRepo(database.percentageDao())}


}