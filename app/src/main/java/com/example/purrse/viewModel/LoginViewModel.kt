package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.User
import com.example.purrse.repo.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepo): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> =_loginState

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun loginUser() {
        val user = _username.value.trim()
        val pass = _password.value.trim()

        if (user.isEmpty() || pass.isEmpty()) {
            _loginState.value = LoginState.Error("Username or password cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                val foundUser = userRepo.login(user, pass)
                if (foundUser != null) {
                    _loginState.value = LoginState.Success(foundUser)
                } else {
                    _loginState.value = LoginState.Error("Invalid credentials")
                }
            } catch(e: Exception){
                    _loginState.value= LoginState.Error("Login Failed: ${e.localizedMessage} ")
                }
            }

        }
        fun resetLoginState(){
            _loginState.value = LoginState.Idle
        }

    }
    sealed class LoginState{
        object Idle : LoginState()
        data class Success(val user: User): LoginState()
        data class Error(val message: String) : LoginState()
    }


