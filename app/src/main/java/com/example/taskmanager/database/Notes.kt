package com.example.taskmanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotesData")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,

    @ColumnInfo(name = "title")
    var title:String?,

    @ColumnInfo(name = "description")
    var description:String?,

    @ColumnInfo(name = "taskChecked")
    var checked: Boolean = false,

    @ColumnInfo(name = "imagePath")
    var imagePath: String = ""
)
