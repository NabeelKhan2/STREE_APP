package com.example.stree20.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface StreeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreeItem(streeItem: StreeItem)

    @Update
    suspend fun updateStreeItem(streeItem: StreeItem)

    @Delete
    suspend fun deleteStreeItem(streeItem: StreeItem)

    @Query("SELECT * FROM Stree_table")
    fun observerAllShoppingItems(): Flow<List<StreeItem>>

}