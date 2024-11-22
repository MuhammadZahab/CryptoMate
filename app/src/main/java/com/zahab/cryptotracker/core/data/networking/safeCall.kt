package com.zahab.cryptotracker.core.data.networking

import com.zahab.cryptotracker.core.domain.util.NetworkError
import com.zahab.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    invoke: () -> HttpResponse
): Result<T, NetworkError> {

    val response = try{
      invoke()

    }
    catch (e: UnresolvedAddressException){
        return Result.Error(NetworkError.NO_INTERNET)
    }
    catch (e: SerializationException){
        return Result.Error(NetworkError.SERIALIZATION)
    }
    catch (e: Exception){
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}