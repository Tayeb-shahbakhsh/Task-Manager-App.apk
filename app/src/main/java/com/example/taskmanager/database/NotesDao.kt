package com.example.taskmanager.database

import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM NotesData")
    fun getAll() : MutableList<Notes>

    @Insert
    fun insert(notes:Notes)

    @Update
    fun update(notes:Notes)

    @Delete
    fun delete(notes:Notes)

    @Query("DELETE FROM NotesData WHERE taskChecked = :status")
    fun deleteTaskDone(status: Boolean)
}