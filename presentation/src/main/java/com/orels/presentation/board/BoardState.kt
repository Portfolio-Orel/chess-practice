package com.orels.presentation.board

import com.orels.domain.model.QuizQuestion

/**
 * @author Orel Zilberman
 * 16/10/2022
 */
data class BoardState(
    val isLoading: Boolean = false,
    val quizQuestions: List<QuizQuestion>? = null,
    val correctAnswersCount: Int = 0,
    val wrongAnswersCount: Int = 0,
) {
    val totalQuestionsCount: Int
        get() = quizQuestions?.size ?: 0
}