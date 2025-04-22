package com.example.cryptohelper.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptohelper.data.database.AppDataBase
import com.example.cryptohelper.data.mapper.CoinMapper
import com.example.cryptohelper.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val apiService = ApiFactory.apiService
    private val coinInfoDao = AppDataBase
        .getInstance(context)
        .coinPriceInfoDao()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = CoinMapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fromSymbs = fSyms)
                val coinInfoDtoList = CoinMapper.mapJsonContainerToCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map { CoinMapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) { }
            delay(10_000)
        }
    }

    companion object {
        const val WORKER_NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

}