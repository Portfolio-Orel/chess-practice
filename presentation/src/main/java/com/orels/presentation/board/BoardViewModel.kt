package com.orels.presentation.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.domain.model.BoardSquareId
import com.orels.domain.model.QuizQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 16/10/2022
 */

@HiltViewModel
class BoardViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(BoardState())

    fun startQuiz() {
        generateQuizQuestions()
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

    private fun generateQuizQuestions() {
        state = state.copy(
            quizQuestions = listOf(
                QuizQuestion(
                    selectedSquare = BoardSquareId.A1, options = listOf(
                        BoardSquareId.A1,
                        BoardSquareId.A2,
                        BoardSquareId.A3
                    )
                )
            )
        )
    }
}