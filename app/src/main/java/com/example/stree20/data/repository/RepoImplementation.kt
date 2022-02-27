package com.example.stree20.data.repository

import android.content.SharedPreferences
import com.example.stree20.data.local.StreeDao
import com.example.stree20.data.local.StreeItem
import com.example.stree20.data.remote.Api
import com.example.stree20.data.remote.response.authresponse.AuthResponse
import com.example.stree20.data.remote.response.channelresponse.ChannelResponse
import com.example.stree20.data.remote.response.msgresponse.MessageResponse
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImplementation @Inject constructor(
    private val streeDao: StreeDao,
    private val api: Api,
    private val sharePreferences: SharedPreferences
) : BaseRepository(), StreeRepo {

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
        return streeDao.observerAllStreeGroup()
    }

    override suspend fun getChannel(token: String): Flow<Resource<ChannelResponse>> =
        safeApiCall { api.getChannels(token) }


    override suspend fun postMessage(
        token: String,
        channel: String,
        text: String
    ): Flow<Resource<MessageResponse>> =
        safeApiCall { api.postMessage(token, channel, text) }


    override suspend fun getAuth(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ): Flow<Resource<AuthResponse>> =
        safeApiCall { api.getAuth(clientId, clientSecret, code, redirectUri) }


    override fun saveCode(code: String) {
        sharePreferences.edit().apply {
            putString(Constants.CODE_KEY, code)
            apply()
        }
    }

    override fun saveToken(token: String) {
        sharePreferences.edit().apply {
            putString(Constants.TOKEN_KEY, token)
            apply()
        }
    }

    override fun getToken(): String? {
        return sharePreferences.getString(Constants.TOKEN_KEY, null)
    }

    override fun getCode(): String? {
        return sharePreferences.getString(Constants.CODE_KEY, null)
    }

}