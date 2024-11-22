package com.zahab.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable
import java.nio.DoubleBuffer

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)
