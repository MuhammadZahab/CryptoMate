package com.zahab.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zahab.cryptotracker.core.domain.util.onError
import com.zahab.cryptotracker.core.domain.util.onSuccess
import com.zahab.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.zahab.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.zahab.cryptotracker.crypto.presentation.models.CoinUi
import com.zahab.cryptotracker.crypto.presentation.models.toCoinPriceUI
import com.zahab.cryptotracker.crypto.presentation.models.toCoinUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: RemoteCoinDataSource
) : ViewModel() {


    private var _state = MutableStateFlow(CoinListState())
    val state = _state

    private var _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

        .onStart { loadCoins(true) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CoinListState()
        )

    fun onAction(coinListActions: CoinListActions) {
        when (coinListActions) {
            CoinListActions.OnRefresh -> {
                loadCoins(false)
            }

            is CoinListActions.SelectCoin -> {
                selectCoin(coinListActions.coinUi)
            }
        }
    }

    private fun loadCoins(isInitialized: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = isInitialized,
                    isRefresh = !isInitialized
                )
            }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefresh = false,
                            coins = coins.map { it.toCoinUI() },
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isRefresh = false) }
                    _events.send(CoinListEvent.Error(error))
                }

        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5L),
                end = ZonedDateTime.now()
            )

                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }

        }


    }
}