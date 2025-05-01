package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.User
import com.example.purrse.repo.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val userRepo: UserRepo): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadUser(userId: Int){
        viewModelScope.launch {
            _user.value = userRepo.getUserByID(userId)
        }
    }

    fun logout(){
        _user.value = null
    }

}