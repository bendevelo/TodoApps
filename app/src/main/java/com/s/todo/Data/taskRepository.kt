package com.s.todo.Data

import com.s.todo.model.task


class taskRepository(
    private val taskDao: taskDao
) {

    suspend fun insertTask(task: task) {
        taskDao.insertTask(task)
    }

    fun getAllTask(): List<task> {
        return taskDao.getAllTask()
    }
    fun getAllTaskbyDue(due :String): List<task> {
        return taskDao.getAllTaskbyDue(due)
    }
    fun getAllTaskbyProgress(status:String): List<task> {
        return taskDao.getAllTaskbyStatus(status)
    }

    suspend  fun updateTask(task: task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: task) {
        taskDao.deleteTask(task)
    }


}
