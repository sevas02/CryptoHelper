package com.example.cryptohelper.domain

class GetCoinInfoListUseCase(
    private val repo: CoinRepository
) {
    operator fun invoke() = repo.getCoinInfoList()
}