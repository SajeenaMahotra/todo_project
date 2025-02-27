package com.example.project_todo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_todo.R
import com.example.project_todo.model.TaskModel

class TaskAdapter(
    var context: Context,
    var data: ArrayList<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDueDate: TextView = itemView.findViewById(R.id.taskDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.task_card, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskTitle.text = data[position].taskName
        holder.taskDueDate.text = data[position].date


    }

    fun updateData(tasks: List<TaskModel>) {
        data.clear()
        data.addAll(tasks)
        notifyDataSetChanged()
    }

    fun getTaskId(position: Int): String {
        return data[position].taskId
    }
}