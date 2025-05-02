package com.example.purrse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.purrse.viewModel.LoginViewModel
import com.example.purrse.data.AppDatabase
import com.example.purrse.repo.UserRepo
import com.example.purrse.viewModel.factory.LoginViewModelFactory
import android.widget.Toast
import com.example.purrse.data.CategoryDao
import kotlinx.coroutines.flow.collect
import com.example.purrse.viewModel.LoginState
import com.example.purrse.viewModel.RegisterState
import kotlinx.coroutines.launch
import com.example.purrse.viewModel.RegisterViewModel
import com.example.purrse.viewModel.factory.RegisterViewModelFactory
import com.example.purrse.model.User



class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLoginRedirect: TextView

    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect)

        // Setup ViewModel + Factory
        val db = AppDatabase.getDatabase(this)
        val factory = RegisterViewModelFactory(
            UserRepo(db.userDao()),
            db.categoryDao()             // ← pass it here
        )
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        // Observe registration state
        lifecycleScope.launch {
            registerViewModel.registerState.collect { state ->
                when (state) {
                    is RegisterState.Success -> {
                        Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_LONG).show()
                        // Redirect to LoginActivity
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish() // Close RegisterActivity
                    }
                    is RegisterState.Error -> {
                        Toast.makeText(this@RegisterActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                    else -> { /* Idle */ }
                }
            }
        }

        // Handle registration logic
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // Simple validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Trigger registration
            val user = User(username = username, // make sure your User has this constructor
                password = password)
            registerViewModel.RegisterUser(user)
        }

        // Redirect to login page if the user already has an account
        tvLoginRedirect.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close RegisterActivity
        }
    }

}