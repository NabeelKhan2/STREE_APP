package com.example.stree20.data.remote

import com.example.stree20.data.remote.response.authresponse.AuthResponse
import com.example.stree20.data.remote.response.channelresponse.ChannelResponse
import com.example.stree20.data.remote.response.msgresponse.MessageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @GET("/api/conversations.list")
    suspend fun getChannels(
        @Header("Authorization") token: String
    ): Response<ChannelResponse>

    @POST("/api/chat.postMessage")
    suspend fun postMessage(
        @Header("Authorization") token: String,
        @Query("channel") channel: String,
        @Query("text") text: String
    ): Response<MessageResponse>

    @POST("/api/oauth.access")
    suspend fun getAuth(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String
    ): Response<AuthResponse>

}