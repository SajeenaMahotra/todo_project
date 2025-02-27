package com.example.project_todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_todo.R
import com.example.project_todo.databinding.ActivitySignUpBinding
import com.example.project_todo.model.UserModel
import com.example.project_todo.repository.UserRepositoryImpl
import com.example.project_todo.viewmodel.UserViewModel

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel= UserViewModel(repo)

        binding.loginText.setOnClickListener {
            val intent = Intent(
                this@SignUpActivity,
                LoginActivity :: class.java
            )
            startActivity(intent)
        }

        binding.buttonSignup.setOnClickListener {
            var fullname = binding.nameInput.text.toString()
            var email = binding.emailInput.text.toString()


            if(fullname.isEmpty() || email.isEmpty()){
                Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var password = binding.passwordInput.text.toString()
            var confirmpassword = binding.confirmpasswordInput.text.toString()
            if (password != confirmpassword ){
                Toast.makeText(this, "Password do not match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                userViewModel.signup(email,password){
                        success,message,userId->
                    if (success){
                        var userModel= UserModel(
                            userId.toString(),
                            fullname,email
                        )
                        addUser(userModel)
                        binding.nameInput.text?.clear()
                        binding.emailInput.text?.clear()
                        binding.passwordInput.text?.clear()
                        binding.confirmpasswordInput.text?.clear()
                    }else{
                        Toast.makeText(
                            this@SignUpActivity,
                            message,Toast.LENGTH_LONG
                        ).show()

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

    private fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId,userModel) { success, message ->
            if (success) {
                Toast.makeText(
                    this@SignUpActivity,
                    message, Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@SignUpActivity,
                    message, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}