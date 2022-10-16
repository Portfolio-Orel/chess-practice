package com.orels.presentation.board

import com.orels.presentation.board.components.BoardSquareId

/**
 * @author Orel Zilberman
 * 16/10/2022
 */
data class BoardState(
    val isLoading: Boolean = false,
    val quizQuestions: List<QuizQuestion>? = null
)

class QuizQuestion(
    val selectedSquare: BoardSquareId,
    val options: List<BoardSquareId>,
    private val maxTries: Int = 1
) {
    private var triesDone = 0
    var state = State.Default

    fun checkAnswer(id: BoardSquareId): Boolean =
        if (triesDone >= maxTries) {
            false
        } else if (id == selectedSquare) {
            state = State.Succeeded
            true
        } else {
            triesDone += 1
            if (triesDone >= maxTries) {
                state = State.Failed
            }
            false
        }

    enum class State {
        Default,
        Succeeded,
        Failed;
    }
}