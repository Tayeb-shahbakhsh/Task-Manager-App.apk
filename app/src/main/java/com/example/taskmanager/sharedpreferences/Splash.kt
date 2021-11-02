package com.example.taskmanager.sharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.view.AddNotesActivity
import com.example.taskmanager.view.LoginActivity
import com.example.taskmanager.adapter.AppConst
import com.example.taskmanager.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //check login go to main activity - else go to login activity
        if (App.preferences.getString(AppConst.USERNAME, "").equals("")){
            startActivity(Intent(this , LoginActivity::class.java))
        }else startActivity(Intent(this , AddNotesActivity::class.java))
    }
}