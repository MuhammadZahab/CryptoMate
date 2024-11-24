package com.zahab.cryptotracker.crypto.presentation.coin_list

import android.annotation.SuppressLint
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import com.zahab.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.zahab.cryptotracker.crypto.presentation.models.CoinUi
import org.junit.Rule
import org.junit.Test

class CoinListScreenTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun showLoadingIndicator_whenStateIsLoading() {

        val state = CoinListState(isLoading = true, isRefresh = false)

        rule.setContent {
            CoinListScreen(state = state, onAction = {})
        }


        rule.onNode(hasTestTag("loadingIndicator"))
            .assertExists("Loading indicator should be visible")
    }

    @Test
    fun coinItems_areDisplayed_whenLoadingComplete() {
        val coins = listOf(previewCoin.copy(name = "Bitcoin"), previewCoin.copy(name = "Ethereum"))
        val state = CoinListState(isLoading = false, coins = coins)

        rule.setContent {
            CoinListScreen(state = state, onAction = {})
        }

        // Check that the coin items are displayed
        for (coin in coins) {
            rule.onNodeWithText(coin.name).assertIsDisplayed()
        }
    }

    @Test
    fun onCoinItemClick_actionIsTriggered() {
        val coins = listOf(previewCoin.copy(name = "Bitcoin"), previewCoin.copy(name = "Ethereum"))
        val state = CoinListState(isLoading = false, coins = coins)
        var selectedCoin: CoinUi? = null

        rule.setContent {
            CoinListScreen(
                state = state,
            ) { action ->
                if (action is CoinListActions.SelectCoin) {
                    selectedCoin = action.coinUi
                }
            }
        }

        // Click on the "Bitcoin" item
        rule.onNodeWithText("Bitcoin").performClick()
        assert(selectedCoin?.name == "Bitcoin")
    }


    @SuppressLint("CheckResult")
    @Test
    fun invokeOnRefreshAction_whenSwipeRefreshTriggered() {

        val state = CoinListState(isLoading = false, isRefresh = false)
        var onRefreshTriggered = false


        rule.setContent {
            CoinListScreen(
                state = state,
                onAction = { action ->
                    if (action is CoinListActions.OnRefresh) {
                        onRefreshTriggered = true

                    }
                }
            )

        }

        rule.onNodeWithTag("swipeRefresh") // Ensure the tag is correct
            .performTouchInput {
                swipeDown()
            }

        assert(onRefreshTriggered) { "OnRefresh action should be triggered on swipe down" }

    }
}