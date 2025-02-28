package com.example.project_todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_todo.R
import com.example.project_todo.databinding.ActivityUpdateTaskBinding
import com.example.project_todo.repository.TaskRepositoryImpl
import com.example.project_todo.ui.fragment.HomeFragment
import com.example.project_todo.viewmodel.TaskViewModel

class UpdateTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateTaskBinding
    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var repo=TaskRepositoryImpl()
        taskViewModel=TaskViewModel(repo)

        var taskId:String=intent.getStringExtra("tasks").toString()

        taskViewModel.getTaskById(taskId) { task, success, message ->
            if (success && task != null) {
                binding.updateTitle.setText(task.taskName)
                binding.updateDate.setText(task.date)
            } else {
                Toast.makeText(this@UpdateTaskActivity, message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdateTask.setOnClickListener {
            var title =binding.updateTitle.text.toString()
            var date =binding.updateDate.text.toString()

            var updatedData=mutableMapOf<String,Any>()

            updatedData["taskName"]=title
            updatedData["date"]=date


            taskViewModel.updateTask(taskId,updatedData){
                success,message ->
                if (success){
                    Toast.makeText(this@UpdateTaskActivity,message,Toast.LENGTH_LONG).show()

                    binding.updateTitle.text.clear()
                    binding.updateDate.text.clear()
                }
            }
        }

        binding.backArrow.setOnClickListener {
            onBackPressed()  // ✅ Simply go back to the previous screen
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}