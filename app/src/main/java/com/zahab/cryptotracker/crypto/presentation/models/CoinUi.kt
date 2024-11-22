package com.zahab.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.zahab.cryptotracker.crypto.domain.Coin
import com.zahab.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.zahab.cryptotracker.crypto.domain.CoinPrice
import com.zahab.cryptotracker.crypto.presentation.coin_detail.DataPoint
import java.text.NumberFormat
import java.time.ZonedDateTime
import java.util.Locale

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList()
)

data class CoinPriceUi(
    val priceUsd: Double,
    val dateTime: ZonedDateTime,
)


data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUI(): CoinUi {

    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayPriceNumber(),
        marketCapUsd = marketCapUSD.toDisplayPriceNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayPriceNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

fun Double.toDisplayPriceNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumIntegerDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)

    )
}

fun CoinPrice.toCoinPriceUI(): CoinPriceUi {
    return CoinPriceUi(
        priceUsd = priceUsd,
        dateTime = dateTime
    )
}
