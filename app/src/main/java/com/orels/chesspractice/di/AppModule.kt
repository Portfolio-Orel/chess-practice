package com.orels.chesspractice.di

import com.orels.domain.use_case.GenerateQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author Orel Zilberman
 * 17/10/2022
 */

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideGenerateQuestionsUseCase(generateQuestionsUseCase: GenerateQuestionsUseCase): GenerateQuestionsUseCase =
        generateQuestionsUseCase
}