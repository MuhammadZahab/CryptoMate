package com.zahab.cryptotracker.core.presentation.util

import android.content.Context
import com.zahab.cryptotracker.R
import com.zahab.cryptotracker.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.request_time_out
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.SERIALIZATION -> R.string.serialization_error
        NetworkError.UNKNOWN -> R.string.unknown_error
    }

    return context.getString(resId)
}