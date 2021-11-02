package com.example.taskmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.taskmanager.adapter.AppConst
import com.example.taskmanager.databinding.ActivityDescBinding

class DescActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        binding.noteTitle.text = intent.getStringExtra(AppConst.TITLE)
        binding.noteDescription.text = intent.getStringExtra(AppConst.DESC)
        val imagePath = intent.getStringExtra("imagePath")


        Glide.with(this)
            .load(imagePath)
            .into(binding.descImageView)
    }
}