package com.s.todo.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.s.todo.model.task
import com.s.todo.model.user


@Dao
interface taskDao {

    //create task
    @Insert
    suspend fun insertTask(task: task)

    //get task
    @Query("SELECT * FROM task")
    fun getAllTask(): List<task>

    //get task in calender
    @Query("SELECT * FROM task WHERE due =:due")
    fun getAllTaskbyDue(due:String): List<task>

    //get task in calender
    @Query("SELECT * FROM task WHERE status ==:status")
    fun getAllTaskbyStatus(status:String): List<task>

    //update task
    @Update
    suspend fun updateTask(task: task)

    //delete task
    @Delete
    suspend fun deleteTask(task: task)

}
