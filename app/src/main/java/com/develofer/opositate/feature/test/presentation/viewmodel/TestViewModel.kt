package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.test.data.model.TestItem
import com.develofer.opositate.feature.test.domain.usecase.GetTestListUseCase
import com.develofer.opositate.feature.test.utils.toTestItem
import com.develofer.opositate.main.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getTestListUseCase: GetTestListUseCase
) : ViewModel() {

    private val _tests = MutableStateFlow(emptyList<TestItem>())
    val tests: StateFlow<List<TestItem>> get() = _tests.asStateFlow()

    init {
        getTestList()
    }

    private fun getTestList() {
        viewModelScope.launch {
            when (val result = getTestListUseCase()) {
                is Result.Success -> {
                    _tests.value = result.data.map { psTestVO -> psTestVO.toTestItem() }
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