package com.example.stree20.di

import android.content.Context
import androidx.room.Room
import com.example.stree20.data.local.StreeDatabase
import com.example.stree20.utils.constants.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        StreeDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: StreeDatabase) =
        db.StreeDao() // The reason we can implement a Dao for the database

}