package com.example.stree20.data.repository

import com.example.stree20.data.local.StreeItem
import com.example.stree20.data.remote.response.authresponse.AuthResponse
import com.example.stree20.data.remote.response.channelresponse.ChannelResponse
import com.example.stree20.data.remote.response.msgresponse.MessageResponse
import com.example.stree20.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface StreeRepo {

    suspend fun insertStreeItem(streeItem: StreeItem)
    suspend fun deleteStreeItem(streeItem: StreeItem)
    suspend fun updateStreeItem(streeItem: StreeItem)
    fun observeAllStreeItem(): Flow<List<StreeItem>>


    fun getChannel(
        token: String
    ): Flow<Resource<ChannelResponse>>

    fun postMessage(
        token: String,
        channel: String,
        text: String
    ): Flow<Resource<MessageResponse>>

    fun getAuth(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ): Flow<Resource<AuthResponse>>

}