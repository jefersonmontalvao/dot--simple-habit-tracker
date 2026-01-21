package com.example.dot__simple_habit_tracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot__simple_habit_tracker.data.repository.InAppReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InAppReviewViewModel @Inject constructor(
    private val inAppReviewRepository: InAppReviewRepository
) : ViewModel() {
    val canRequestInAppReview: Flow<Boolean> = inAppReviewRepository.canRequestInAppReview

    fun onReviewRequested() {
        viewModelScope.launch {
            inAppReviewRepository.markHasRequestedReview()
        }
    }
}