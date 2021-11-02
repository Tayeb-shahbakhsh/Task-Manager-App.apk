package com.example.taskmanager.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application() {

    //setup shared preferences

    lateinit var context: Context

    companion object{
        lateinit var preferences : SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()
        editor.apply()
        //
    }

}