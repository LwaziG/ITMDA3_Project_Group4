package com.naveenapps.expensemanager

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.GetOnboardingStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.GetCurrentThemeUseCase
import com.naveenapps.expensemanager.core.model.Theme
import com.naveenapps.expensemanager.feature.theme.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

//MainViewModel
@HiltViewModel
class MainViewModel @Inject constructor(
    getCurrentThemeUseCase: GetCurrentThemeUseCase,
    getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
) : ViewModel() {

    private val _currentTheme = MutableStateFlow(
        Theme(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            R.string.choose_theme,
        ),
    )
    val currentTheme = _currentTheme.asStateFlow()

    //onboardingStatus
    private val _onboardingStatus = MutableStateFlow<Boolean?>(null)
    val onboardingStatus = _onboardingStatus.asStateFlow()

    init {
        getCurrentThemeUseCase.invoke().onEach {
            _currentTheme.value = it
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            _onboardingStatus.value = getOnboardingStatusUseCase.invoke()
        }
    }
}
