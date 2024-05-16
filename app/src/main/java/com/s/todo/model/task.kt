package com.s.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class task(


    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long?,
    val title: String,
    val description: String,
    val due: String,
    val reminder: Int,
    val status: String,
    val file: String,
    val type: String
)