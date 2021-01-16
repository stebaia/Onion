package com.sbaiardi.onion.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sbaiardi.onion.data.dao.PercentageDao
import com.sbaiardi.onion.data.model.Percentages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Percentages::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {


        abstract fun percentageDao(): PercentageDao

    private class PercentageDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var perDao = database.percentageDao()

                    // Delete all content here.
                    perDao.deleteAll()

                    // Add sample words.
                    var percentage = Percentages("2020-02-24T18:00:00",0,0,0.0)
                    perDao.insertData(percentage)
                }
            }
        }
    }


    companion object {

            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "covid_db"
                    ).addCallback(PercentageDatabaseCallback(scope)).allowMainThreadQueries().build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }




            /*private val LOCK = Any()

            operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
            }

            private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "CovidDataBase")
                    .build()

            /*fun getDatabaseCovid(context: Context): AppDatabase {

                if (INSTANCE != null) return INSTANCE!!

                synchronized(this) {

                    INSTANCE = Room
                        .databaseBuilder(context, AppDatabase::class.java, "CovidDataBase")
                        .fallbackToDestructiveMigration()
                        .build()

                    return INSTANCE!!

             */

                }
            }*/


        }
}