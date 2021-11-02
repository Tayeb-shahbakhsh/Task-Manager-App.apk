package com.example.taskmanager.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.taskmanager.R
import com.example.taskmanager.adapter.Adapter
import com.example.taskmanager.adapter.AppConst
import com.example.taskmanager.database.Notes
import com.example.taskmanager.database.NotesDao
import com.example.taskmanager.database.NotesDataBase
import com.example.taskmanager.databinding.ActivityAddNotesBinding
import com.example.taskmanager.workmanager.myWoker
import java.security.cert.CertPath
import java.util.concurrent.TimeUnit

class AddNotesActivity : AppCompatActivity(), Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var notesDao: NotesDao

    private val list = mutableListOf<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        notesDao = NotesDataBase.buildDataBase(this).getNotesDao()
        getNotesFromDB()



        //setup adapter
        adapter = Adapter(list, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        setupWorkManager()
        //
        //if fab button clicked show dialog
        binding.addNoteButton.setOnClickListener {

            val intent = Intent(this, MakeNoteActivity::class.java)
            startActivityForResult(intent, 1)

        }
    }

    private fun setupWorkManager() {
        val request = PeriodicWorkRequest.Builder(myWoker::class.java,15,TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(request)  
    }


    //if note clicked go to note special activity
    override fun onItemClick(position: Int) {
        val intent = Intent(this, DescActivity::class.java)
        intent.putExtra(AppConst.TITLE, list[position].title)
        intent.putExtra(AppConst.DESC, list[position].description)
        intent.putExtra("imagePath", list[position].imagePath)
        startActivity(intent)
    }

    override fun onUpdate(notes: Notes) {
        notesDao.update(notes)
    }

    private fun addNotesToDB(notes: Notes) {
        notesDao.insert(notes)
    }

    private fun getNotesFromDB() {

        val allNotes = notesDao.getAll()
        list.addAll(allNotes)

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == 1 && requestCode == 1) {

                val notes =  Notes(
                    title = data?.getStringExtra("title").toString(),
                    description = data?.getStringExtra("desc").toString(),
                    imagePath = data?.getStringExtra("imagePath").toString()
                    )
                list.add(notes)

                addNotesToDB(notes)
                adapter.notifyDataSetChanged()

        }
    }
}