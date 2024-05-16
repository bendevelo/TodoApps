package com.s.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s.todo.Data.taskRepository
import com.s.todo.model.task
import kotlinx.coroutines.launch

class viewModel(val repository: taskRepository) : ViewModel() {


    val task = MutableLiveData<List<task>>()


    fun getTrx(): List<task> {
        val result = repository.getAllTask()
        return result
    }

    fun getTaskbyStatus(status: String): List<task> {
        val result = repository.getAllTaskbyProgress(status)
        return result
    }

    fun getTaskbyDue(due: String): List<task> {
        val result = repository.getAllTaskbyDue(due)
        return result
    }

    fun insertTask(tsk: task) {
        viewModelScope.launch {
            repository.insertTask(tsk)
        }

    }

    fun updateTask(tsk: task) {
        viewModelScope.launch {
            repository.updateTask(tsk)
        }
    }

     fun deleteTask(tsk: task) {
        viewModelScope.launch {
            repository.deleteTask(tsk)
        }
    }


}