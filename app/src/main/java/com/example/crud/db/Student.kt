package com.example.crud.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("student_id")
    val id: Int,
    @ColumnInfo("student_name")
    val name: String,
    @ColumnInfo("student_email")
    val email: String
)
