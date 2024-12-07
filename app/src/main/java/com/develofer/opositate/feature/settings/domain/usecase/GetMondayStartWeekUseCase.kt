package com.develofer.opositate.feature.settings.domain.usecase

import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMondayStartWeekUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getMondayStartWeek()
}