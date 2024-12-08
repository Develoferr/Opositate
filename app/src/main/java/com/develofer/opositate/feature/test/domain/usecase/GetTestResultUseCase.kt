package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.data.model.SolvedQuestion
import com.develofer.opositate.feature.profile.data.model.SolvedTest
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserIdUseCase
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestResultUseCase @Inject constructor(
    private val solvedTestRepository: SolvedTestRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getTestUseCase: GetTestUseCase,
    private val getTaskStringResIdUseCase: GetTaskStringResIdUseCase,
    private val getTestTypeByIdUseCase: GetTestTypeByIdUseCase,
    private val resourceProvider: ResourceProvider,
) {
    suspend operator fun invoke(
        solvedTestId: Int,
        testTypeId: Int,
        difficultId: Int?,
        groupId: Int?,
        abilityId: Int?,
        taskId: Int?
    ): Result<SolvedTest> {
        // Get User ID task
        val userIdResult = getUserIdUseCase()
        if (userIdResult is Result.Error) {
            return Result.Error(userIdResult.exception)
        }

        // Get Test Result task
        val userId = (userIdResult as Result.Success).data
        val testType = getTestTypeByIdUseCase(testTypeId)
        val testSolvedResult = solvedTestRepository.getTestResult(solvedTestId.toString(), userId, testType)
        if (testSolvedResult is Result.Error) {
            return Result.Error(testSolvedResult.exception)
        }

        // Get Test task
        val testResult = getTestUseCase(testTypeId, difficultId, groupId, abilityId, taskId)
        if (testResult is Result.Error) {
            return Result.Error(testResult.exception)
        }

        // Generate SolvedTest by combining test results and base test
        val testSolved = (testSolvedResult as Result.Success).data
        val test = (testResult as Result.Success).data
        return if (testSolved == null || test == null) Result.Error(Exception(resourceProvider.getString(
            R.string.error_message__test_data_missing)))
            else {
                Result.Success(
                    SolvedTest(
                        id = solvedTestId.toString(),
                        testId = solvedTestId,
                        completionTime = testSolved.completionTime,
                        questions = mapQuestions(test, testSolved),
                        name = "${solvedTestId + 1}. ${resourceProvider.getString(getTaskStringResIdUseCase(abilityId ?: 0, taskId ?: 0))}",
                        maxTime = testSolved.maxTime,
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

