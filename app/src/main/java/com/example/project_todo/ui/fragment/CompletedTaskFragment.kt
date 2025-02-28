package com.example.project_todo.ui.fragment

import android.app.AlertDialog
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
        // Add swipe-to-delete functionality
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskId = adapter.getTaskId(viewHolder.adapterPosition)

                // Show confirmation dialog
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete") { _, _ ->
                        taskViewModel.deleteTask(taskId) { success, message ->
                            if (success) {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    .setCancelable(false)
                    .show()
            }
        }).attachToRecyclerView(binding.completedRecyclerView)
    }



}