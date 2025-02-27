package com.example.project_todo.repository

import com.example.project_todo.model.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepositoryImpl:TaskRepository {

    val database:FirebaseDatabase=FirebaseDatabase.getInstance()
    val ref : DatabaseReference = database.reference.child("tasks")

    override fun addTask(taskModel: TaskModel, callback: (Boolean, String) -> Unit) {
        val id = ref.push().key.toString()
        taskModel.taskId = id
        ref.child(id).setValue(taskModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Task added successfully")
            } else {
                callback(false, it.exception?.message ?: "Error adding task")
            }
        }

    }

    override fun updateTask(
        taskId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(taskId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Task updated successfully")
            } else {
                callback(false, it.exception?.message ?: "Error updating task")
            }
        }

    }

    override fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit) {
        ref.child(taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Task deleted successfully")
            } else {
                callback(false, it.exception?.message ?: "Error deleting task")
            }
        }
    }

    override fun getTaskById(taskId: String, callback: (TaskModel?, Boolean, String) -> Unit) {
        ref.child(taskId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var model=snapshot.getValue(TaskModel::class.java)
                    callback(model,true,"Product fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }

        })

    }

    override fun getAllTasks(callback: (List<TaskModel>?, Boolean, String) -> Unit) {

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var products = mutableListOf<TaskModel>()
                    for (eachData in snapshot.children){
                        var model = eachData.getValue(TaskModel::class.java)
                        if (model !=null){
                            products.add(model)
                        }
                    }
                    callback(products,true,"Product fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }

        })
    }

}