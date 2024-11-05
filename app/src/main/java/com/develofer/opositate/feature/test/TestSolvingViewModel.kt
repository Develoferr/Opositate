package com.develofer.opositate.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.PsTest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestSolvingViewModel @Inject constructor(
    private val getTestUseCase: GetTestUseCase
) : ViewModel() {

    private val _test = MutableStateFlow<PsTest?>(null)
    val test: StateFlow<PsTest?> = _test

    fun getTest(testId: String) {
        viewModelScope.launch {
            _test.value = getTestUseCase(testId)
        }
    }
}