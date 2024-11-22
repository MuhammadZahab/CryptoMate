package com.zahab.cryptotracker.crypto.presentation.coin_list

import com.zahab.cryptotracker.crypto.presentation.models.CoinUi

data class CoinListState(
    val isLoading:Boolean = false,
    val isRefresh:Boolean = false,
    val coins:List<CoinUi>  = emptyList(),
    val selectedCoin: CoinUi? = null
)