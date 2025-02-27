package com.example.project_todo.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_todo.R
import com.example.project_todo.adapter.TaskAdapter
import com.example.project_todo.databinding.FragmentHomeBinding
import com.example.project_todo.repository.TaskRepositoryImpl
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.TaskViewModel
import com.example.project_todo.viewmodel.UserViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var userViewModel: UserViewModel
    lateinit var taskViewModel: TaskViewModel
    lateinit var adapter: TaskAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val taskRepo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(taskRepo)

        adapter = TaskAdapter(requireContext(), arrayListOf())
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTasks.adapter = adapter

        var currentUser = userViewModel.getCurrentUser()
        currentUser.let {
            userViewModel.getUserFromDatabase(it?.uid.toString())
        }

        userViewModel.userData.observe(requireActivity()){
            binding.fullNameDisplay.text = it?.fullName.toString()
        }

        // Observe tasks and update UI
        taskViewModel.getAllTasks.observe(viewLifecycleOwner) { taskList ->
            taskList?.let {
                adapter.updateData(it)
            }
        }

        taskViewModel.getAllTasks()

        // Enable swipe to delete tasks
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Fix: Must return a boolean
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskId = adapter.getTaskId(viewHolder.adapterPosition)
                taskViewModel.deleteTask(taskId) { success, message ->
                    if (success) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }).attachToRecyclerView(binding.recyclerViewTasks)

    }


}