package com.s.todo.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.s.todo.model.task
import com.s.todo.model.user


@Database(entities = [task::class], version = 1)
    abstract class todoDatabase : RoomDatabase() {
        abstract fun taskDao(): taskDao

        companion object {
            private var INSTANCE: todoDatabase? = null

            fun getDatabaseInstance(context: Context): todoDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        todoDatabase::class.java,
                        "todo_db"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
