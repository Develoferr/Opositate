package com.develofer.opositate.feature.settings.domain.usecase

import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetMondayStartWeekUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isMondayStart: Boolean) {
        settingsRepository.setMondayStartWeek(isMondayStart)
    }
}