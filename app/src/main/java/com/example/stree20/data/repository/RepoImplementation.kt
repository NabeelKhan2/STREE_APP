package com.example.stree20.data.repository

import com.example.stree20.data.local.StreeDao
import com.example.stree20.data.local.StreeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImplementation @Inject constructor(
    private val streeDao: StreeDao
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
}