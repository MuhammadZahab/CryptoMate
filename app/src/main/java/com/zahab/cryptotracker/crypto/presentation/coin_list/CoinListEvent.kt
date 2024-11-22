package com.zahab.cryptotracker.crypto.presentation.coin_list

import com.zahab.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error:NetworkError) : CoinListEvent
}