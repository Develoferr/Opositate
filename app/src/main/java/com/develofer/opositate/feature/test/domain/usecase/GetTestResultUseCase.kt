package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.domain.usecase.GetUserIdUseCase
import com.develofer.opositate.feature.profile.data.model.SolvedQuestion
import com.develofer.opositate.feature.profile.data.model.SolvedTest
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import javax.inject.Inject

class GetTestResultUseCase @Inject constructor(
    private val solvedTestRepository: SolvedTestRepository,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getTestUseCase: GetTestUseCase
) {
    suspend operator fun invoke(solvedTestId: String): SolvedTest? {
        val testResult = solvedTestRepository.getTestResult(solvedTestId, getUserIdUseCase())
        val test = getTestUseCase(testResult?.testId.toString())

        val questions = mutableListOf<SolvedQuestion>()
        test?.questions?.forEach {
            questions.add(
                SolvedQuestion(
                    question = it.question,
                    options = it.options,
                    correctAnswer = it.correctAnswer,
                    selectedAnswer = testResult?.questions?.find { question -> question.id == it.id }?.selectedAnswer,
                    id = it.id
                )
            )
        }

        return if (testResult != null && test != null) {
            SolvedTest(
                id = solvedTestId,
                testId = testResult.testId,
                completionTime = testResult.completionTime,
                questions = questions,
                name = test.name,
                maxTime = test.maxTime,
                score = testResult.score
            )
        } else null
    }
}
