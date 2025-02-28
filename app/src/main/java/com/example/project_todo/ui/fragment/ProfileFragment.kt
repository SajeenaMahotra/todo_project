package com.example.project_todo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_todo.R
import com.example.project_todo.databinding.FragmentProfileBinding
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.UserViewModel


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var userViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)




    }

}