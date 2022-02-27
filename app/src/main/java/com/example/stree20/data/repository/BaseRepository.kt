package com.example.stree20.data.repository

import com.example.stree20.utils.helpers.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ) = flow {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful)
                emit(Resource.Success<T>(response.body()!!)) else emit(
                Resource.Error<T>(
                    response.message()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error<T>(e.message ?: ""))
        }
    }
}
