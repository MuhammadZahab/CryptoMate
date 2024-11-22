package com.zahab.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.zahab.cryptotracker.ui.theme.CryptoTrackerTheme


@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListActions) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.isLoading && !state.isRefresh) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Use SwipeRefresh only when not showing the full-screen loading indicator
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isRefresh),
            onRefresh = { onAction(CoinListActions.OnRefresh) }
        ) {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(state.coins) { coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = {
                            onAction(CoinListActions.SelectCoin(coinUi))
                        }
                    )
                }
            }
        }
    }

}

@Preview
@PreviewLightDark
@Composable
fun CoinListScreenPreview() {
    CryptoTrackerTheme {

        CoinListScreen(
            state = CoinListState(
                coins = (1..100).map {
                    previewCoin
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )

    }
}
