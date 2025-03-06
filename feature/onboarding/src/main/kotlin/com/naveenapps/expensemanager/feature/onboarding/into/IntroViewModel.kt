package com.naveenapps.expensemanager.feature.onboarding.into

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.feature.onboarding.UserEntity
import com.naveenapps.expensemanager.feature.onboarding.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val appComposeNavigator: AppComposeNavigator,
    private val repository: UserRepository
) : ViewModel() {

    var loginSuccess: Boolean = false
        private set

    fun navigate() {
        appComposeNavigator.navigate(ExpenseManagerScreens.Onboarding)
    }

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