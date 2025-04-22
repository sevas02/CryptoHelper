package com.example.cryptohelper.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptohelper.data.database.AppDataBase
import com.example.cryptohelper.data.mapper.CoinMapper
import com.example.cryptohelper.data.workers.RefreshDataWorker
import com.example.cryptohelper.domain.CoinInfo
import com.example.cryptohelper.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDao = AppDataBase
        .getInstance(application)
        .coinPriceInfoDao()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map { listCoinInfoDbModel ->
            listCoinInfoDbModel.map {
                CoinMapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map { coinInfoDbModel ->
            CoinMapper.mapDbModelToEntity(coinInfoDbModel)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
            .enqueueUniqueWork(
                RefreshDataWorker.WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                RefreshDataWorker.makeRequest()
            )
    }
}