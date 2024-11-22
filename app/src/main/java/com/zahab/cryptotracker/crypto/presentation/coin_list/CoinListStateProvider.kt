package com.zahab.cryptotracker.crypto.presentation.coin_list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.previewCoin

class CoinListStateProvider : PreviewParameterProvider<CoinListState> {
    override val values: Sequence<CoinListState> = sequenceOf(
        CoinListState(
            coins = (1..10).map { previewCoin },
            isLoading = true,
            isRefresh = false,
            selectedCoin = previewCoin
        ),
        CoinListState(
            coins = (1..10).map { previewCoin },
            isLoading = false,
            isRefresh = false,
            selectedCoin = previewCoin
        ),
        CoinListState(
            coins = (1..2).map { previewCoin },
            isLoading = false,
            isRefresh = false,
            selectedCoin = previewCoin
        )
    )
}
