package com.orels.presentation.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * @author Orel Zilberman
 * 16/10/2022
 */

typealias OnBoardSquareClick = (BoardSquareId) -> Unit

@Composable
fun BoardSquare(
    id: BoardSquareId,
    onClick: OnBoardSquareClick,
    modifier: Modifier = Modifier,
    shouldOpaque: Boolean = false
) {
    Box(
        modifier = modifier
            .background(color = if (shouldOpaque) Color(id.color).copy(alpha = 0.2f) else Color(id.color))
            .clickable {
                onClick(id)
            })
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
    H1(color = whiteColor),
}


private val blackColor = 0xff000000
private val whiteColor = 0xffffffff