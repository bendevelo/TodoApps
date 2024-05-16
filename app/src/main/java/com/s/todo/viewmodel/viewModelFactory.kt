package com.s.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s.todo.Data.taskRepository

class ViewModelFactory(private val repository: taskRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            return viewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}