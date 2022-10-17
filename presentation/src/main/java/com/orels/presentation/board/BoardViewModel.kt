package com.orels.presentation.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.domain.model.BoardSquareId
import com.orels.domain.model.QuizQuestion
import com.orels.domain.use_case.GenerateQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 16/10/2022
 */

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val generateQuestionsUseCase: GenerateQuestionsUseCase
) : ViewModel() {
    private var state by mutableStateOf(BoardState())

    fun startQuiz() {
        state = state.copy(
            quizQuestions = generateQuestionsUseCase()
        )
    }

    fun stopQuiz() {
        state = state.copy(quizQuestions = null)
    }

    fun isQuizStarted(): Boolean = !(state.quizQuestions.isNullOrEmpty())

    fun getCurrentQuestion(): QuizQuestion? =
        state.quizQuestions?.firstOrNull { it.state == QuizQuestion.State.Default }

    fun checkAnswer(question: QuizQuestion, answer: BoardSquareId): Boolean {
        val result = question.checkAnswer(id = answer)
        removeSucceededQuestions()
        return result
    }



    private fun removeSucceededQuestions() {
        state =
            state.copy(quizQuestions = state.quizQuestions?.filter { it.state == QuizQuestion.State.Default })
    }
}