package com.example.stree20.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [StreeItem::class],
    version = 2
)
abstract class StreeDatabase : RoomDatabase() {

    abstract fun StreeDao(): StreeDao

}