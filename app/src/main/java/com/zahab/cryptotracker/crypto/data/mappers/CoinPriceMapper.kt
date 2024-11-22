package com.zahab.cryptotracker.crypto.data.mappers

import com.zahab.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.zahab.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}
