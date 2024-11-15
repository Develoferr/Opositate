package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.usecase.GetTestAsksUseCase
import com.develofer.opositate.main.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getTestAsksUseCase: GetTestAsksUseCase
) : ViewModel() {

    private val _testAsks = MutableStateFlow(emptyList<AbilityAsksItem>())
    val testAsks: StateFlow<List<AbilityAsksItem>> get() = _testAsks.asStateFlow()

    init {
        getTestList()
    }

    private fun getTestList() {
        viewModelScope.launch {
            when (val result = getTestAsksUseCase()) {
                is Result.Success -> {
                    _testAsks.value = result.data?.testByAbilityList ?: emptyList()
                }
                is Result.Error -> {
                    // Handle error
                }
                is Result.Loading -> {
                    // Handle loading
                }
            }
        }
    }
}