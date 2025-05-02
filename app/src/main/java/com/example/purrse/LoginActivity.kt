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
import kotlinx.coroutines.flow.collect
import com.example.purrse.viewModel.LoginState
import kotlinx.coroutines.launch



class LoginActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)

        private lateinit var viewModel: LoginViewModel
        private lateinit var usernameEditText: EditText
        private lateinit var passwordEditText: EditText
        private lateinit var loginButton: Button
        private lateinit var createAccountText: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            // Init views
            usernameEditText = findViewById(R.id.username)
            passwordEditText = findViewById(R.id.password)
            loginButton = findViewById(R.id.loginBtn)
            createAccountText = findViewById(R.id.createAccountLink)

            // Get Repo and VM
            val userDao = AppDatabase.getDatabase(this).userDao()
            val userRepo = UserRepo(userDao)
            val factory = LoginViewModelFactory(userRepo)
            viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

            // Observe state
            lifecycleScope.launch {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Success -> {
                            Toast.makeText(this@LoginActivity, "Welcome ${state.user.username}", Toast.LENGTH_SHORT).show()
                            // Navigate to HomeActivity
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("userId", state.user.userId)
                            startActivity(intent)

                        }
                        is LoginState.Error -> {
                            Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            }

            // On click
            loginButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                // Basic validation
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.updateUsername(username)
                    viewModel.updatePassword(password)
                    viewModel.loginUser()
                } else {
                    Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                }
            }

            createAccountText.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)

            }
        }
    }
