package com.zahab.cryptotracker.crypto.data.mappers

import com.zahab.cryptotracker.crypto.data.networking.dto.CoinDto
import com.zahab.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        marketCapUSD = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}
