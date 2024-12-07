package com.develofer.opositate.feature.login.data

import com.develofer.opositate.R
import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val resourceProvider: ResourceProvider
) : AuthRepository {

    override fun getUser(): Result<FirebaseUser?> {
        return auth.currentUser?.let {
            Result.Success(it)
        } ?: Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated)))
    }

    override suspend fun createUser(email: String, password: String): Result<Unit> =
        if (email.isNotBlank() && password.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { createTask ->
                    if (createTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = createTask.exception?.message ?: resourceProvider.getString(R.string.error_message__registration_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__email_password_blank)))

    override suspend fun updateUsername(username: String): Result<Unit> =
        if (username.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                )?.addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = updateTask.exception?.message ?: resourceProvider.getString(R.string.error_message__profile_update_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                } ?: continuation.resume(Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated))))
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__username_blank)))

    override suspend fun login(email: String, password: String): Result<Unit> =
        if (email.isNotBlank() && password.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { loginTask ->
                    if (loginTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = loginTask.exception?.message ?: resourceProvider.getString(R.string.error_message__login_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__email_password_blank)))

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        if (email.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.sendPasswordResetEmail(email).addOnCompleteListener { sendTask ->
                    if (sendTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage =
                            sendTask.exception?.message ?: resourceProvider.getString(R.string.error_message__password_reset_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__email_blank)))

    override suspend fun updateEmail(newEmail: String): Result<Unit> =
        if (newEmail.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.currentUser?.verifyBeforeUpdateEmail(newEmail)?.addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = updateTask.exception?.message
                            ?: resourceProvider.getString(R.string.error_message__email_update_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                } ?: continuation.resume(Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated))))
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__email_blank)))

    override suspend fun deleteAccount(): Result<Unit> =
        suspendCoroutine { continuation ->
            auth.currentUser?.delete()?.addOnCompleteListener { deleteTask ->
                if (deleteTask.isSuccessful) {
                    continuation.resume(Result.Success(Unit))
                } else {
                    val errorMessage = deleteTask.exception?.message
                        ?: resourceProvider.getString(R.string.error_message__account_deletion_failed)
                    continuation.resume(Result.Error(Exception(errorMessage)))
                }
            } ?: continuation.resume(Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated))))
        }

    override suspend fun reauthenticate(email: String, password: String): Result<Unit> =
        if (password.isNotBlank()) {
            suspendCoroutine { continuation ->
                val credential = EmailAuthProvider.getCredential(email, password)
                auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = reauthTask.exception?.message
                            ?: resourceProvider.getString(R.string.error_message__reauthentication_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception(resourceProvider.getString(R.string.error_message__email_password_blank)))


    override fun logout(): Result<Unit> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}