package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase
import com.develofer.opositate.feature.test.domain.model.TestAsksByGroup
import com.develofer.opositate.feature.test.domain.usecase.GetTestAsksUseCase
import com.develofer.opositate.feature.test.utils.toTestAsksByGroup
import com.develofer.opositate.main.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getTestAsksUseCase: GetTestAsksUseCase,
    private val getAbilityGroupIdUseCase: GetAbilityGroupIdUseCase,
    private val getGroupAbilityResIdUseCase: GetGroupAbilityResIdUseCase,
    private val getGroupResIdIconUseCase: GetGroupResIdIconUseCase,
    private val getAbilityResIdUseCase: GetAbilityResIdUseCase
) : ViewModel() {

    private val _testAsks = MutableStateFlow(emptyList<TestAsksByGroup>())
    val testAsks: StateFlow<List<TestAsksByGroup>> get() = _testAsks.asStateFlow()

    init {
        getTestList()
    }

    private fun getTestList() {
        viewModelScope.launch {
            when (val result = getTestAsksUseCase()) {
                is Result.Success -> {
                    _testAsks.value = (result.data?.testByAbilityList ?: emptyList()).toTestAsksByGroup(
                        getAbilityGroupIdUseCase,
                        getGroupAbilityResIdUseCase,
                        getGroupResIdIconUseCase,
                        getAbilityResIdUseCase
                    )
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