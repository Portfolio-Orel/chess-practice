package com.orels.domain.model

/**
 * @author Orel Zilberman
 * 17/10/2022
 */

class QuizQuestion(
    val selectedSquare: BoardSquareId,
    val options: List<BoardSquareId>,
    private val maxTries: Int = 1
) {

    private var tries: List<BoardSquareId> = emptyList()

    private val triesDone: Int
        get() = tries.size

    var state = State.Default

    fun checkAnswer(id: BoardSquareId): Boolean =
        if (triesDone >= maxTries) {
            false
        } else if (id == selectedSquare) {
            state = State.Succeeded
            true
        } else {
            tries = tries + id
            if (triesDone >= maxTries) {
                state = State.Failed
            }
            false
        }

    fun isOptionDisabled(id: BoardSquareId): Boolean = tries.contains(id)

    enum class State {
        Default,
        Succeeded,
        Failed;
    }
}

enum class BoardSquareId(val color: Long) {
    A8(color = whiteColor),
    A7(color = blackColor),
    A6(color = whiteColor),
    A5(color = blackColor),
    A4(color = whiteColor),
    A3(color = blackColor),
    A2(color = whiteColor),
    A1(color = blackColor),

    B8(color = blackColor),
    B7(color = whiteColor),
    B6(color = blackColor),
    B5(color = whiteColor),
    B4(color = blackColor),
    B3(color = whiteColor),
    B2(color = blackColor),
    B1(color = whiteColor),

    C8(color = whiteColor),
    C7(color = blackColor),
    C6(color = whiteColor),
    C5(color = blackColor),
    C4(color = whiteColor),
    C3(color = blackColor),
    C2(color = whiteColor),
    C1(color = blackColor),

    D8(color = blackColor),
    D7(color = whiteColor),
    D6(color = blackColor),
    D5(color = whiteColor),
    D4(color = blackColor),
    D3(color = whiteColor),
    D2(color = blackColor),
    D1(color = whiteColor),

    E8(color = whiteColor),
    E7(color = blackColor),
    E6(color = whiteColor),
    E5(color = blackColor),
    E4(color = whiteColor),
    E3(color = blackColor),
    E2(color = whiteColor),
    E1(color = blackColor),

    F8(color = blackColor),
    F7(color = whiteColor),
    F6(color = blackColor),
    F5(color = whiteColor),
    F4(color = blackColor),
    F3(color = whiteColor),
    F2(color = blackColor),
    F1(color = whiteColor),

    G8(color = whiteColor),
    G7(color = blackColor),
    G6(color = whiteColor),
    G5(color = blackColor),
    G4(color = whiteColor),
    G3(color = blackColor),
    G2(color = whiteColor),
    G1(color = blackColor),

    H8(color = blackColor),
    H7(color = whiteColor),
    H6(color = blackColor),
    H5(color = whiteColor),
    H4(color = blackColor),
    H3(color = whiteColor),
    H2(color = blackColor),
    H1(color = whiteColor);

    val letter: Char
        get() = name[0]

    /**
     * A - 0
     * B - 1
     * ...
     * H - 7
     */
    val letterNumber: Int
        get() = name[0].number

    val number: Int
        get() = name[1].digitToInt()

    companion object {
        private const val ASCII_OF_A = 65

        @Throws(NumberGreaterThan7Exception::class, NumberLessThan0Exception::class)
        fun letterFromNumber(number: Int): Char {
            if (number > 7) throw NumberGreaterThan7Exception
            if (number < 0) throw NumberLessThan0Exception
            return values().first { it.letterNumber == number }.letter
        }

        @Throws(
            NumberGreaterThan8Exception::class,
            NumberGreaterThan7Exception::class,
            NumberLessThan1Exception::class,
            NumberLessThan0Exception::class
        )
        fun convert(letter: Char, number: Int): BoardSquareId? {
            if (number > 8) throw NumberGreaterThan8Exception
            if (number < 1) throw NumberLessThan1Exception
            if (letter.number > 7) throw NumberGreaterThan7Exception
            if (letter.number < 0) throw NumberLessThan0Exception
            return values().firstOrNull { it.name == "$letter$number" }
        }

        // Range: 0 - 7
        private val Char.number
            get() = code - ASCII_OF_A
    }
}

private const val blackColor = 0xff000000
private const val whiteColor = 0xffffffff

object NumberGreaterThan7Exception : Exception("The number for the letter must be lower than 7")
object NumberLessThan0Exception : Exception("The number for the letter must be bigger than 0")

object NumberGreaterThan8Exception : Exception("The number must be lower than 8")
object NumberLessThan1Exception : Exception("The number must be bigger than 1")
