package com.example.project_todo.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.project_todo.R
import com.example.project_todo.databinding.FragmentAddTaskBinding
import com.example.project_todo.model.TaskModel
import com.example.project_todo.repository.TaskRepositoryImpl

import com.example.project_todo.viewmodel.TaskViewModel
import java.util.Calendar


class AddTaskFragment : Fragment() {
    lateinit var binding:FragmentAddTaskBinding
    lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo= TaskRepositoryImpl()
        taskViewModel=TaskViewModel(repo)


        binding.btnAddTask.setOnClickListener {
            addTask()
        }

        binding.editTextDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun addTask() {
        val title = binding.editTextTitle.text.toString().trim()
        val date = binding.editTextDate.text.toString().trim()

        if (title.isEmpty() || date.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()

            return
        }

        val model = TaskModel("", title, date)

        taskViewModel.addTask(model) { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "Task added successfully!", Toast.LENGTH_LONG).show()

                // Clear input fields after successful task addition
                binding.editTextTitle.text.clear()
                binding.editTextDate.text.clear()
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.editTextDate.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


}