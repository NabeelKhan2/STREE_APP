package com.example.stree20.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.stree20.data.local.StreeDatabase
import com.example.stree20.data.remote.Api
import com.example.stree20.data.repository.RepoImplementation
import com.example.stree20.data.repository.StreeRepo
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.constants.Constants.BASE_URL
import com.example.stree20.utils.constants.Constants.DATABASE_NAME
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideYourDao(db: StreeDatabase) =
        db.StreeDao() // The reason we can implement a Dao for the database


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideCoroutineScope() = CoroutineScope(Job())

    @Singleton
    @Provides
    fun getMyRepository(defaultShoppingRepo: RepoImplementation): StreeRepo =
        defaultShoppingRepo

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE
        )


}