package com.example.stree20.data.repository

import com.example.stree20.data.local.StreeItem
import kotlinx.coroutines.flow.Flow

interface StreeRepo {

    suspend fun insertStreeItem(streeItem: StreeItem)
    suspend fun deleteStreeItem(streeItem: StreeItem)
    suspend fun updateStreeItem(streeItem: StreeItem)
    fun observeAllStreeItem(): Flow<List<StreeItem>>

}