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
        // Get User ID task
        val userIdResult = getUserIdUseCase()
        if (userIdResult is Result.Error) {
            return Result.Error(userIdResult.exception)
        }

        // Get Test Result task
        val userId = (userIdResult as Result.Success).data
        val testSolvedResult = solvedTestRepository.getTestResult(solvedTestId, userId)
        if (testSolvedResult is Result.Error) {
            return Result.Error(testSolvedResult.exception)
        }

        // Get Test task
        val testResultId = (testSolvedResult as Result.Success).data?.testId.toString()
        val testResult = getTestUseCase(testResultId)
        if (testResult is Result.Error) {
            return Result.Error(testResult.exception)
        }

        // Generate SolvedTest by combining the results and the base test
        val testSolved = (testSolvedResult).data
        val test = (testResult as Result.Success).data
        return if (testSolved == null || test == null) Result.Error(Exception("Test data or test is missing"))
            else {
                Result.Success(
                    SolvedTest(
                        id = solvedTestId,
                        testId = test.id,
                        completionTime = testSolved.completionTime,
                        questions = mapQuestions(test, testSolved),
                        name = test.name,
                        maxTime = test.maxTime,
                        score = testSolved.score
                    )
                )
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

