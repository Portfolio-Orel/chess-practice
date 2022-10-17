package com.orels.presentation.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.orels.domain.model.BoardSquareId

/**
 * @author Orel Zilberman
 * 16/10/2022
 */

typealias OnBoardSquareClick = (BoardSquareId) -> Unit

@Composable
fun BoardSquare(
    id: BoardSquareId,
    modifier: Modifier = Modifier,
    disabled: Boolean = false
) {
    val squareModifier =
        if (disabled) modifier.background(color = Color(id.color).copy(alpha = 0.03f))
        else modifier
            .background(color = Color(id.color))
    Box(
        modifier = squareModifier
    )
}
