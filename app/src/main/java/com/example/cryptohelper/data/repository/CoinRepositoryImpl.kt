package com.example.cryptohelper.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptohelper.data.database.AppDataBase
import com.example.cryptohelper.data.mapper.CoinMapper
import com.example.cryptohelper.data.network.ApiFactory
import com.example.cryptohelper.domain.CoinInfo
import com.example.cryptohelper.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val apiService = ApiFactory.apiService

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

    override suspend fun loadData() {
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
}