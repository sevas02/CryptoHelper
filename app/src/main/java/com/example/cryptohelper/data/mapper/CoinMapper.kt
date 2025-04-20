package com.example.cryptohelper.data.mapper

import com.example.cryptohelper.data.database.CoinInfoDbModel
import com.example.cryptohelper.data.network.model.CoinInfoDto
import com.example.cryptohelper.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptohelper.data.network.model.CoinNamesListDto
import com.example.cryptohelper.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object CoinMapper {

    private const val BASE_IMAGE_URL = "https://www.cryptocompare.com"

    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = coinInfoDto.fromSymbol,
        toSymbol = coinInfoDto.toSymbol,
        price = coinInfoDto.price,
        lastUpdate = coinInfoDto.lastUpdate,
        highDay = coinInfoDto.highDay,
        lowDay = coinInfoDto.lowDay,
        lastMarket = coinInfoDto.lastMarket,
        imageUrl = BASE_IMAGE_URL + coinInfoDto.imageUrl
    )

    fun mapJsonContainerToCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val jsonObject = jsonContainer.json ?: return ArrayList<CoinInfoDto>()
        return jsonObject.keySet().flatMap { coinKey ->
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            currencyJson.keySet().map { currencyKey ->
                Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
            }
        }
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",").toString()
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        val stamp = Timestamp(timestamp.let { it?.times(1000) ?: 0 })
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)
    }
}