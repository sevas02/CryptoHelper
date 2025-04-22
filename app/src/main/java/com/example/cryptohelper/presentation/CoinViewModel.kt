package com.example.cryptohelper.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptohelper.data.repository.CoinRepositoryImpl
import com.example.cryptohelper.domain.GetCoinInfoListUseCase
import com.example.cryptohelper.domain.GetCoinInfoUseCase
import com.example.cryptohelper.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSymb: String) = getCoinInfoUseCase(fSymb)

    init {
        loadDataUseCase()
    }
}