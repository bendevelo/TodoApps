package com.s.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class user(

    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long?,
    val name: String,
    val email: String,
    val password: String

)