package com.example.stree20.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Stree_table")
data class StreeItem(
    val groupName: String,
    val source: Int,
    val channel: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
):Parcelable
