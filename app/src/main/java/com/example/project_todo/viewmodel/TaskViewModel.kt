package com.example.project_todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_todo.model.TaskModel
import com.example.project_todo.repository.TaskRepository

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    val tasks = MutableLiveData<TaskModel>()
    val getAllTasks = MutableLiveData<List<TaskModel>>()

    fun addTask(taskModel: TaskModel, callback: (Boolean, String) -> Unit) {
        repo.addTask(taskModel) { success, message ->
            if (success) {
                callback(true, message)
            } else {
                callback(false, message)
            }
        }
    }

    fun updateTask(taskId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        repo.updateTask(taskId, data, callback)
    }

    fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteTask(taskId, callback)
    }

    fun getTaskById(taskId: String, callback: (TaskModel?, Boolean, String) -> Unit) {
        repo.getTaskById(taskId) { task, success, message ->
            if (success) {
                tasks.value = task
            }
            callback(task, success, message)
        }
    }

    fun getAllTasks(callback: (List<TaskModel>?, Boolean, String) -> Unit = { _, _, _ -> }) {
        repo.getAllTasks { taskList, success, message ->
            if (success) {
                getAllTasks.value = taskList
            }
            callback(taskList, success, message)
        }
    }

}
