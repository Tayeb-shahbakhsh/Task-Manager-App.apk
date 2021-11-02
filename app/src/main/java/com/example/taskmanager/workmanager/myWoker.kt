package com.example.taskmanager.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.taskmanager.database.NotesDataBase


class myWoker(val context: Context, val workerParameters: WorkerParameters): Worker(context, workerParameters) {


    override fun doWork(): Result {

        val notesDao = NotesDataBase.buildDataBase(context).getNotesDao()
        notesDao.deleteTaskDone(true)

        return Result.success()
    }
}