package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.User
import com.example.purrse.repo.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo: UserRepo): ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun RegisterUser(user: User){
        viewModelScope.launch {
            try {
                val existingUser = userRepo.getUserByUsername(user.username)
                if (existingUser != null){
                    _registerState.value = RegisterState.Error("Username already exists")
                } else {
                    userRepo.registerUser(user)
                    _registerState.value = RegisterState.Success
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Registration failed: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _registerState.value = RegisterState.Idle
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
