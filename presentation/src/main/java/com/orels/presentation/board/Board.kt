package com.orels.presentation.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.presentation.R
import com.orels.presentation.board.components.BoardSquare
import com.orels.presentation.board.components.BoardSquareId

/**
 * @author Orel Zilberman
 * 16/10/2022
 */

@Composable
fun Board(modifier: Modifier = Modifier, viewModel: BoardViewModel = hiltViewModel()) {
    val listState = rememberLazyGridState()
    val configuration = LocalConfiguration.current
    val boardPadding: Dp = 8.dp
    val boardSquareSize: Dp = ((configuration.screenWidthDp / 8).dp - boardPadding)

    Column(
        modifier = modifier
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box() {
            LazyVerticalGrid(
                columns = GridCells.Fixed(8),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                state = listState,
                contentPadding = PaddingValues(boardPadding),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(
                    count = BoardSquareId.values().size,
                    key = { index ->
                        index
                    }
                ) { index ->
                    val id = BoardSquareId.values()[BoardSquareId.values().size - 1 - index]
                    BoardSquare(
                        modifier = Modifier
                            .size(boardSquareSize),
                        id = id,
                        onClick = { println(BoardSquareId.values()[index]) },
                        shouldOpaque = viewModel.isQuizStarted() && viewModel.getCurrentQuestion()?.selectedSquare != id
                    )
                }
            }
        }
        if (viewModel.isQuizStarted()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val question = viewModel.getCurrentQuestion()
                    question?.options?.forEach { id ->
                        Button(colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primary,
                        ), onClick = { question.checkAnswer(id) }) {
                            Text(text = "$id")
                        }
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary,
                    ), onClick = viewModel::stopQuiz
                ) {
                        Text(text = stringResource(R.string.end_quiz))
                }
            }
        } else {
            Button(
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primary,
                ), onClick = viewModel::startQuiz
            ) {
                Text(text = stringResource(R.string.start_quiz))
            }
        }
    }
}