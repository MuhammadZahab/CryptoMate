package com.zahab.cryptotracker.crypto.presentation.coin_list

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.zahab.cryptotracker.ui.theme.CryptoTrackerTheme


@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier,
    onAction: (CoinListActions) -> Unit,
) {

    if (state.isLoading && !state.isRefresh) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.testTag("loadingIndicator")
            )
        }
    } else {
        // Use SwipeRefresh only when not showing the full-screen loading indicator
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isRefresh),
            onRefresh = { onAction(CoinListActions.OnRefresh) },
            modifier = modifier.testTag("swipeRefresh")
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

@PreviewLightDark
@Composable
fun CoinListScreenPreview(
    @PreviewParameter(CoinListStateProvider::class) state: CoinListState
) {
    CryptoTrackerTheme {
        CoinListScreen(
            state = state,
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}
