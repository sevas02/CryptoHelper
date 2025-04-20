package com.example.cryptohelper.data.network

import com.example.cryptohelper.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptohelper.data.network.model.CoinNamesListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMB) toSymb: String = CURRENSY
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMBS) fromSymbs: String,
        @Query(QUERY_PARAM_TO_SYMBS) toSymbs: String = CURRENSY
    ): CoinInfoJsonContainerDto

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMB = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_TO_SYMBS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBS = "fsyms"
        private const val API_KEY = "a87e83d78b1848e67194bc392f0cde2f267b0a2038065dc9aeba0a28a79fea22"

        private const val CURRENSY = "USD"
    }
}