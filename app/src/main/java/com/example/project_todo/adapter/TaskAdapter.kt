package com.example.project_todo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_todo.R
import com.example.project_todo.model.TaskModel
import com.example.project_todo.ui.activity.UpdateTaskActivity

class TaskAdapter(
    var context: Context,
    var data: ArrayList<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDate: TextView = itemView.findViewById(R.id.taskDate)
        val editTask: ImageView = itemView.findViewById(R.id.editTask)
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
        holder.taskDate.text = data[position].date

        holder.editTask.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java)
            intent.putExtra("tasks", data[position].taskId)
            holder.itemView.context.startActivity(intent)
        }

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