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
                val testResult = solvedTestRepository.getTestResult(solvedTestId, userId)
                val test = getTestUseCase(testResult?.testId.toString())
                if (testResult != null && test != null) {
                    Result.Success(
                        SolvedTest(
                            id = solvedTestId,
                            testId = testResult.testId,
                            completionTime = testResult.completionTime,
                            questions = mapQuestions(test, testResult),
                            name = test.name,
                            maxTime = test.maxTime,
                            score = testResult.score
                        )
                    )
                } else {
                    Result.Error(Exception("Test result or test data is missing"))
                }
            }
            is Result.Error -> userIdResult
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
