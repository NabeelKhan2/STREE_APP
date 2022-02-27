package com.example.stree20

import android.app.Application
import android.content.Intent
import com.example.stree20.service.SmsService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StreeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Intent(this , SmsService::class.java).also {
            startService(it)
        }
    }
}