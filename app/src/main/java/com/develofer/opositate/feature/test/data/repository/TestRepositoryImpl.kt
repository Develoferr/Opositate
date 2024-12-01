package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.R
import com.develofer.opositate.feature.test.data.model.AbilityTestsResponse
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.feature.test.utils.toAbilityTest
import com.develofer.opositate.feature.test.utils.toGeneralTest
import com.develofer.opositate.feature.test.utils.toGroupTest
import com.develofer.opositate.feature.test.utils.toTaskTest
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val resourceProvider: ResourceProvider
): TestRepository {

    // testId = 0..9 Se pueden generar un total de 10 test por habilidad y por dificultad diferentes
    // de 20 preguntas (4 preguntas por tarea x 5 tareas) = 20 preguntas
    // 40 preguntas por tarea x 5 tareas = 200 preguntas (200 preguntas / 20 preguntas/test) = 10 tests
    override suspend fun getTestByAbility(abilityId: Int, difficultyId: Int, testId: Int?): Result<PsTest?> {
        return try {
            val testDocument = getTestCollection().document(abilityId.toString()).get().await()
            val testResponse = if (testDocument.exists()) testDocument.toObject(AbilityTestsResponse::class.java) else null
            val test = testResponse?.toAbilityTest(difficultyId, testId ?: 0)
            Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // testId = 0..1 Se pueden generar un total de 2 test por tarea y por dificultad diferentes
    // de 20 preguntas (40 preguntas por tarea / 20 preguntas/test) = 2 tests
    override suspend fun getTestByTask(abilityId: Int, taskId: Int, testId: Int, difficultyId: Int): Result<PsTest?> {
        return try {
            val testDocument = getTestCollection().document(abilityId.toString()).get().await()
            val testResponse = if (testDocument.exists()) testDocument.toObject(AbilityTestsResponse::class.java) else null
            val test = testResponse?.toTaskTest(difficultyId, abilityId, taskId, testId)
            Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // testId = 0..39 Se pueden generar un total de 40 test por habilidad y por dificultad diferentes
    // de 5 preguntas por habilidad en el grupo
    // 1 pregunta por tarea por habilidad x 40 preguntas por test/tarea/habilidad = 40 tests
    override suspend fun getTestByGroup(abilityIdList: List<Int>, difficultyId: Int, testId: Int?): Result<PsTest?> {
        return try {
            val testDocumentList = mutableListOf<AbilityTestsResponse>()
            abilityIdList.forEach { abilityId ->
                val testDocument = getTestCollection().document(abilityId.toString()).get().await()
                val testResponse = if (testDocument.exists()) testDocument.toObject(AbilityTestsResponse::class.java) else null
                testResponse?.let { testDocumentList.add(it) }
            }
            val test = testDocumentList.toGroupTest(difficultyId, testId ?: 0)
            Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // testId = 0..99 Se pueden generar un total de 100 test generales diferentes
    // de 32 preguntas por dificultad (16 abilidades x 2 preguntas)
    // 40 preguntas por habilidad por tarea x 5 tareas = 200 preguntas habilidad/tarea (200 preguntas / 2 preguntas/abilidad/test) = 100 tests
    override suspend fun getTestGeneral(difficultyId: Int, testId: Int): Result<PsTest?> {
        return try {
            val testDocumentList = getTestCollection().get().await()
                .mapNotNull { it.toObject(AbilityTestsResponse::class.java) }
            val test = testDocumentList.toGeneralTest(difficultyId, testId)
            Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getTestCollection(): CollectionReference =
        firestore.collection(resourceProvider.getString(R.string.firebase_constant__tests))
}