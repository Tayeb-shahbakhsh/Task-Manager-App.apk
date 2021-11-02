 package com.example.taskmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskmanager.adapter.AppConst
import com.example.taskmanager.databinding.LoginActivityBinding
import com.example.taskmanager.sharedpreferences.App

 class LoginActivity : AppCompatActivity() {

    lateinit var binding : LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //REMOVE NIGHT MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //FULL SCREEN
         this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide(); //hide the title bar
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //when login button clicked go to main activity
        binding.loginButton.setOnClickListener {

            //get inputs value as string
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            //check if inputs are null dont go to next activity
            if (username != "" && password != ""){
                App.editor.putString(AppConst.USERNAME , username).apply()
                App.editor.putString(AppConst.PASSWORD, password).apply()
                val intent = Intent(this, AddNotesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}