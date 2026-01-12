package com.example.dot__simple_habit_tracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot__simple_habit_tracker.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val settingsRepository: SettingsRepository
) : ViewModel() {

    val darkMode = settingsRepository.darkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    fun toggleDarkMode(enable: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(enable)
        }
    }
}