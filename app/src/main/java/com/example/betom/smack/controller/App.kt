package com.example.betom.smack.controller

import android.app.Application
import com.example.betom.smack.utilities.SharedPreferences

class App : Application() {

    companion object {
        lateinit var sharePreferences: SharedPreferences
    }

    override fun onCreate() {
        sharePreferences = SharedPreferences(applicationContext)
        super.onCreate()
    }
}