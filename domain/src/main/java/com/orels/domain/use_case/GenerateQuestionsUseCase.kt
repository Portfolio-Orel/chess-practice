package com.orels.domain.use_case

import com.orels.domain.model.BoardSquareId
import com.orels.domain.model.QuizQuestion
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Orel Zilberman
 * 17/10/2022
 */
class GenerateQuestionsUseCase @Inject constructor() {

    private val allIds = BoardSquareId.values().toList()
    private var idsToUse: ArrayList<BoardSquareId> = ArrayList()

    /**
     * Generates 64 random questions for each square, where no square
     * is repeated twice.
     * @param maxRange is the max range from which to generate an option
     * from around the selected square
     * @param numberOfOptions is the number of options per question
     */
    @Throws(Exception::class)
    operator fun invoke(maxRange: Int = 2, numberOfOptions: Int = 3): List<QuizQuestion> {
        if(maxRange < 2) throw Exception("maxRange must be at least 2")
        val questions: ArrayList<QuizQuestion> = ArrayList()
        idsToUse = ArrayList(allIds)

        while (idsToUse.isNotEmpty()) {
            val squareId = getRandomId()
            val randomOptions = generateOptions(
                squareId = squareId,
                range = maxRange,
                count = numberOfOptions
            )
            questions.add(
                QuizQuestion(
                    correctSquare = squareId,
                    options = randomOptions,
                    maxTries = 1
                )
            )
            removeIdFromUsedIds(squareId)
        }
        return questions
    }

    /**
     * Returns the upper boundary of letters within [range]
     */
    fun getUpperBoundaryLetter(squareId: BoardSquareId, range: Int): Int =
        if (squareId.letterNumber >= 7 - range) 7 else squareId.letterNumber + range

    fun getLowerBoundaryLetter(squareId: BoardSquareId, range: Int): Int =
        if (squareId.letterNumber <= 0 + range) 0 else squareId.letterNumber - range

    fun getUpperBoundaryNumber(squareId: BoardSquareId, range: Int): Int =
        if (squareId.number >= 8 - range) 8 else squareId.number + range

    fun getLowerBoundaryNumber(squareId: BoardSquareId, range: Int): Int =
        if (squareId.number <= 1 + range) 1 else squareId.number - range


    /**
     * Returns a random id from [idsToUse]
     */
    private fun getRandomId(): BoardSquareId = idsToUse.random()
    private fun removeIdFromUsedIds(id: BoardSquareId) = idsToUse.remove(id)

    private fun generateRandomLetter(id: BoardSquareId, range: Int): Char? {
        val lowerBoundaryLetter = getLowerBoundaryLetter(squareId = id, range = range)
        val upperBondaryLetter = getUpperBoundaryLetter(squareId = id, range = range)

        val letterNumberGenerated =
            Random.nextInt(from = lowerBoundaryLetter, until = upperBondaryLetter)

        return kotlin.runCatching { BoardSquareId.letterFromNumber(letterNumberGenerated) }
            .getOrNull()
    }

    private fun generateRandomNumber(id: BoardSquareId, range: Int): Int {
        val lowerBoundaryNumber = getLowerBoundaryNumber(squareId = id, range = range)
        val upperBondaryNumber = getUpperBoundaryNumber(squareId = id, range = range)

        return Random.nextInt(from = lowerBoundaryNumber, until = upperBondaryNumber)
    }

    private fun generateRandomSquare(id: BoardSquareId, range: Int): BoardSquareId? {
        val randomLetter = generateRandomLetter(id = id, range = range) ?: return null
        val randomNumber = generateRandomNumber(id = id, range = range)
        return BoardSquareId.convert(letter = randomLetter, number = randomNumber)
    }

    /**
     * Generates [count] amount of options, where the options
     * are squares around [squareId] within a range of [range]
     */
    @Suppress("SameParameterValue")
    private fun generateOptions(
        squareId: BoardSquareId,
        range: Int,
        count: Int = 3
    ): List<BoardSquareId> {
        val options = ArrayList<BoardSquareId>()
        options.add(squareId)
        for (i in 0 until count - 1) {
            var randomSquare =  generateRandomSquare(id = squareId, range = range)
            while(options.contains(randomSquare)) {
                randomSquare = generateRandomSquare(id = squareId, range = range)
            }
            randomSquare?.let {
                options.add(it)
            }
        }
        return options.shuffled()
    }

    companion object {
        const val MAX_NUMBER_OF_QUESTIONS = 64
    }

}
