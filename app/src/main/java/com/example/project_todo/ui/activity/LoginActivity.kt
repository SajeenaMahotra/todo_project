package com.example.project_todo.ui.activity


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_todo.R
import com.example.project_todo.databinding.ActivityLoginBinding
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel=UserViewModel(repo)

        binding.createoneText.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                SignUpActivity::class.java
            )
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email:String = binding.inputEmail.text.toString()
            val password :String = binding.inputPassword.text.toString()

            if(email.isEmpty()){
                binding.inputEmail.error="Please enter your email"
            }else if(password.isEmpty()){
                binding.inputPassword.error="Please enter the password"
            }else{
                userViewModel.login(email,password){
                        success,message->
                    if(success){
                        Toast.makeText(this@LoginActivity,message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity,NavigationActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()

                    }
                }

            }

        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}