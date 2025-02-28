package com.example.project_todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_todo.model.TaskModel
import com.example.project_todo.repository.TaskRepository

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskModel>>()  // ✅ Correct type
    val tasks: LiveData<List<TaskModel>> get() = _tasks

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

    fun getTaskById(taskId: String,callback: (TaskModel?, Boolean,String) -> Unit){
        repo.getTaskById(taskId) { task, success, message ->
            if (success) {
                callback(task, true, "Task fetched successfully")
            } else {
                callback(null, false, message)
            }
        }
    }

    fun getAllTasks(userId: String, callback: (List<TaskModel>?,Boolean,String) -> Unit){
        repo.getAllTasks(userId) { taskList, success, message ->
            if (success) {
                _tasks.value = taskList
            }
            callback(taskList, success, message)
        }

    }

    fun markTaskAsComplete(taskId: String, isCompleted: Boolean, callback: (Boolean, String) -> Unit){
        repo.markTaskAsComplete(taskId,isCompleted,callback)
    }
}
