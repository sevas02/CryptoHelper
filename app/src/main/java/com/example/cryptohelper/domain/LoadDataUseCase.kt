package com.example.cryptohelper.domain

class LoadDataUseCase(
    private val repo: CoinRepository
) {
    suspend operator fun invoke() = repo.loadData()
}