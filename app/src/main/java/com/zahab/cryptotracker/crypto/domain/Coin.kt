package com.zahab.cryptotracker.crypto.domain

import java.time.LocalDate

data class Coin(
    val id:String,
    val name:String,
    val rank:Int,
    val symbol:String,
    val marketCapUSD:Double,
    val priceUsd:Double,
    val changePercent24Hr: Double,
)