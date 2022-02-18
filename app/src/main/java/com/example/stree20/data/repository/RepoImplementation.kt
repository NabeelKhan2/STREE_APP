package com.example.stree20.data.repository

import com.example.stree20.data.local.StreeDao
import com.example.stree20.data.local.StreeItem
import com.example.stree20.data.remote.Api
import com.example.stree20.data.remote.response.authresponse.AuthResponse
import com.example.stree20.data.remote.response.channelresponse.ChannelResponse
import com.example.stree20.data.remote.response.msgresponse.MessageResponse
import com.example.stree20.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoImplementation @Inject constructor(
    private val streeDao: StreeDao,
    private val api: Api
) : StreeRepo {

    override suspend fun insertStreeItem(streeItem: StreeItem) {
        streeDao.insertStreeItem(streeItem)
    }

    override suspend fun deleteStreeItem(streeItem: StreeItem) {
        streeDao.deleteStreeItem(streeItem)
    }

    override suspend fun updateStreeItem(streeItem: StreeItem) {
        streeDao.updateStreeItem(streeItem)
    }

    override fun observeAllStreeItem(): Flow<List<StreeItem>> {
        return streeDao.observerAllShoppingItems()
    }


    override fun getChannel(token: String): Flow<Resource<ChannelResponse>> =
        flow {
            try {
                val response = api.getChannels(token)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    emit(Resource.Success(result))
                } else {
                    emit(Resource.Error<ChannelResponse>(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<ChannelResponse>(e.message ?: "An error occurred"))
            }
        }


    override fun postMessage(
        token: String,
        channel: String,
        text: String
    ): Flow<Resource<MessageResponse>> =
        flow {
            try {
                val response = api.postMessage(token, channel, text)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    emit(Resource.Success(result))
                } else {
                    emit(Resource.Error<MessageResponse>(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<MessageResponse>(e.message ?: "An error occurred"))
            }
        }


    override fun getAuth(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ): Flow<Resource<AuthResponse>> =
        flow {
            try {
                val response = api.getAuth(clientId, clientSecret, code, redirectUri)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    emit(Resource.Success(result))
                } else {
                    emit(Resource.Error<AuthResponse>(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<AuthResponse>(e.message ?: "An error occurred"))
            }
        }

}