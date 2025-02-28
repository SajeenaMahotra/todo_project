package com.example.project_todo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_todo.R
import com.example.project_todo.adapter.TaskAdapter
import com.example.project_todo.databinding.FragmentCompletedTaskBinding
import com.example.project_todo.repository.TaskRepositoryImpl
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.TaskViewModel
import com.example.project_todo.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class CompletedTaskFragment : Fragment() {
    lateinit var binding: FragmentCompletedTaskBinding
    lateinit var userViewModel: UserViewModel
    lateinit var taskViewModel: TaskViewModel
    lateinit var adapter: TaskAdapter
    private var userId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCompletedTaskBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val taskRepo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(taskRepo)

        adapter = TaskAdapter(requireContext(), arrayListOf(),taskViewModel)
        binding.completedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.completedRecyclerView.adapter = adapter


        userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->
        taskViewModel.getAllTasks(userId!!) { taskList, success, message ->
            if (success) {
                val completedTasks = taskList?.filter { it.completed } ?: emptyList()
                adapter.updateData(completedTasks)
            }
        }
        }

    }


}