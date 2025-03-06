package com.naveenapps.expensemanager.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var loginSuccess: Boolean = false
        private set

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val user = UserEntity(email = email, password = password)
            repository.insertUser(user)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(email, password)
            loginSuccess = user != null
        }
    }
}
