package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.data.model.SolvedQuestion
import com.develofer.opositate.feature.profile.data.model.SolvedTest
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.domain.usecase.GetUserIdUseCase
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class GetTestResultUseCase @Inject constructor(
    private val solvedTestRepository: SolvedTestRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getTestUseCase: GetTestUseCase
) {
    suspend operator fun invoke(solvedTestId: String): Result<SolvedTest> {
        return when (val userIdResult = getUserIdUseCase()) {
            is Result.Success -> {
                val userId = userIdResult.data
                when (val testResult = solvedTestRepository.getTestResult(solvedTestId, userId)) {
                    is Result.Success -> {
                        when (val test = getTestUseCase(testResult.data?.testId.toString())) {
                            is Result.Success -> {
                                if (testResult.data != null && test.data != null) {
                                    Result.Success(
                                        SolvedTest(
                                            id = solvedTestId,
                                            testId = testResult.data.testId,
                                            completionTime = testResult.data.completionTime,
                                            questions = mapQuestions(test.data, testResult.data),
                                            name = test.data.name,
                                            maxTime = test.data.maxTime,
                                            score = testResult.data.score
                                        )
                                    )
                                } else {
                                    Result.Error(Exception("Test result or test data is missing"))
                                }
                            }
                            is Result.Error -> Result.Error(test.exception)
                            is Result.Loading -> Result.Loading
                        }
                    }
                    is Result.Error -> Result.Error(testResult.exception)
                    is Result.Loading -> Result.Loading
                }
            }
            is Result.Error -> Result.Error(userIdResult.exception)
            is Result.Loading -> Result.Loading
        }
    }

    private fun mapQuestions(test: PsTest, testResult: TestResult): List<SolvedQuestion> =
        test.questions.map { question ->
            SolvedQuestion(
                question = question.question,
                options = question.options,
                correctAnswer = question.correctAnswer,
                selectedAnswer = testResult.questions.find { it.id == question.id }?.selectedAnswer,
                id = question.id
            )
        }
}

