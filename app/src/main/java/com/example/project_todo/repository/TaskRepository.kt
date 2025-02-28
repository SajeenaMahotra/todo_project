package com.example.project_todo.repository

import com.example.project_todo.model.TaskModel
import com.google.android.gms.tasks.Task

interface TaskRepository {
    fun addTask(taskModel: TaskModel,callback:(Boolean,String)->Unit)

    fun updateTask(taskId:String,data: MutableMap<String,Any>,callback: (Boolean, String) -> Unit)

    fun deleteTask(taskId: String,callback: (Boolean, String) -> Unit)

    fun getTaskById(taskId: String,callback: (TaskModel?, Boolean,String) -> Unit)

    fun getAllTasks(userId: String, callback: (List<TaskModel>?,Boolean,String) -> Unit)

    fun markTaskAsComplete(taskId: String, isCompleted: Boolean, callback: (Boolean, String) -> Unit)
}
