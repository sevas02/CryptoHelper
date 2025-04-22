package com.example.cryptohelper.domain

class LoadDataUseCase(
    private val repo: CoinRepository
) {
    operator fun invoke() = repo.loadData()
}