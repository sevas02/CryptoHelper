package com.example.cryptohelper.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinInfoDbModel::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao

    companion object{
        private var db: AppDataBase? = null

        private const val NAME_DB = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    NAME_DB
                ).fallbackToDestructiveMigration(true).build()
                db = instance
                return instance
            }
        }
    }
}