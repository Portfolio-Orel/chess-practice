package com.orels.domain

import com.orels.domain.model.BoardSquareId
import com.orels.domain.model.QuizQuestion
import com.orels.domain.use_case.GenerateQuestionsUseCase
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GenerateQuestionsUseCaseTest {

    private val maxRange = 2
    private val numberOfOptions = 3
    private val generateQuestionsUseCase = GenerateQuestionsUseCase()

    @Test
    fun questions_is_valid() {
        val questions =
            generateQuestionsUseCase(maxRange = maxRange, numberOfOptions = numberOfOptions)
        check_max_number_of_questions(questions = questions)
        questions.forEach { question ->
            check_number_of_options(question)
            check_contains_right_answer(question)
            check_options_unique(question)
            question.options.forEach { option ->
                check_range_of_option(option = option, square = question.selectedSquare)
            }
        }
    }

    private fun check_options_unique(question: QuizQuestion) =
        assert(question.options.toSet().size == question.options.size)

    private fun check_contains_right_answer(
        question: QuizQuestion
    ) =
        assert(question.options.contains(question.selectedSquare))

    private fun check_max_number_of_questions(questions: List<QuizQuestion>) =
        assert(questions.size == GenerateQuestionsUseCase.MAX_NUMBER_OF_QUESTIONS)

    private fun check_number_of_options(question: QuizQuestion) =
        assert(question.options.size == numberOfOptions)

    private fun check_range_of_option(square: BoardSquareId, option: BoardSquareId) {
        val upperBoundaryLetter =
            generateQuestionsUseCase.getUpperBoundaryLetter(squareId = square, range = maxRange)
        val lowerBoundaryLetter =
            generateQuestionsUseCase.getLowerBoundaryLetter(squareId = square, range = maxRange)
        val upperBoundaryNumber =
            generateQuestionsUseCase.getUpperBoundaryNumber(squareId = square, range = maxRange)
        val lowerBoundaryNumber =
            generateQuestionsUseCase.getLowerBoundaryNumber(squareId = square, range = maxRange)

        assert(option.letterNumber in lowerBoundaryLetter..upperBoundaryLetter)
        assert(option.number in lowerBoundaryNumber..upperBoundaryNumber)
    }

}