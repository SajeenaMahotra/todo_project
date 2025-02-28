package com.example.project_todo.model

data class TaskModel(
    var taskId: String = "",
    var taskName: String = "",
    var date: String = "",
    val userId: String = "",
    var completed: Boolean = false
)
