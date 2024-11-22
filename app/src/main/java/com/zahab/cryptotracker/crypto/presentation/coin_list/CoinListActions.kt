package com.zahab.cryptotracker.crypto.presentation.coin_list

import com.zahab.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListActions {
    data class SelectCoin(val coinUi: CoinUi) : CoinListActions
    data object OnRefresh : CoinListActions
}