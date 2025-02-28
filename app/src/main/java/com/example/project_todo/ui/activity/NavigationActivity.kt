package com.example.project_todo.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.project_todo.R
import com.example.project_todo.databinding.ActivityNavigationBinding
import com.example.project_todo.ui.fragment.AddTaskFragment
import com.example.project_todo.ui.fragment.CompletedTaskFragment
import com.example.project_todo.ui.fragment.HomeFragment
import com.example.project_todo.ui.fragment.ProfileFragment

class NavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavigationBinding

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction : FragmentTransaction =fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameDashboard,fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding =ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavBar.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                    R.id.navAddTask -> replaceFragment(AddTaskFragment())
                        R.id.navCompletedTask -> replaceFragment(CompletedTaskFragment())
                            R.id.navProfile -> replaceFragment(ProfileFragment())
                                else ->{}
            }
            true
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

}
