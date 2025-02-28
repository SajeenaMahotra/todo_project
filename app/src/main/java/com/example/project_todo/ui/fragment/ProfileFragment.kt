package com.example.project_todo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.project_todo.R
import com.example.project_todo.databinding.FragmentProfileBinding
import com.example.project_todo.model.UserModel
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var userViewModel: UserViewModel
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(layoutInflater,container,false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        loadUserProfile()

        binding.appCompatButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val userId = auth?.currentUser?.uid
        val newName = binding.nameDisplay.text.toString().trim()
        val newEmail = binding.emailDisplay.text.toString().trim()

        if (userId != null) {
            val updatedUser = UserModel(fullName = newName, email = newEmail)
            userViewModel.addUserToDatabase(userId, updatedUser) { success, message ->
                if (success) {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid  // Now properly initialized
        if (userId != null) {
            userViewModel.getUserFromDatabase(userId)
            userViewModel.userData.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    binding.nameDisplay.setText(user.fullName)
                    binding.emailDisplay.setText(user.email)
                } else {
                    Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }

    }

}