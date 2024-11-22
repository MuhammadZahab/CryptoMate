@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.zahab.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zahab.cryptotracker.core.presentation.util.ObserveAsEvents
import com.zahab.cryptotracker.core.presentation.util.toString
import com.zahab.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.zahab.cryptotracker.crypto.presentation.coin_list.CoinListActions
import com.zahab.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.zahab.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.zahab.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCoinListDetailPane(
    viewModel: CoinListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is CoinListEvent.Error ->
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_SHORT
                ).show()
        }

    }
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    modifier = modifier,
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            CoinListActions.OnRefresh -> {}
                            is CoinListActions.SelectCoin -> {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    }
                )
            }

        },
        detailPane = {

           AnimatedPane {

               CoinDetailScreen(state = state,modifier = modifier)
           }

        }
    )
}