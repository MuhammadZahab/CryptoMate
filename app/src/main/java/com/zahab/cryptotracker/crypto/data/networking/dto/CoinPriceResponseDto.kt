package com.zahab.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceResponseDto(
    val data: List<CoinPriceDto>
)