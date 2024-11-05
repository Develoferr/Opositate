package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.PsTestVO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestListUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(): List<PsTestVO> {
        return testRepository.getTestList()
    }
}