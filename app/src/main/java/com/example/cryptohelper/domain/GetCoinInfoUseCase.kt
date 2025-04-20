package com.example.cryptohelper.domain

class GetCoinInfoUseCase(
    private val repo: CoinRepository
) {
    operator fun invoke(fromSymbol: String) = repo.getCoinInfo(fromSymbol)
}