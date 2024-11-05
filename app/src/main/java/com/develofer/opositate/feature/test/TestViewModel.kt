package com.develofer.opositate.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.toTestItem
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
            val psTestList = getTestListUseCase()
            _tests.value = psTestList.map { psTestVO -> psTestVO.toTestItem() }
        }
    }
}