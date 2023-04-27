package com.nytimes.xwdcompose.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.nytimes.xwdcompose.data.*
import com.nytimes.xwdcompose.data.SelectionMode.*
import com.nytimes.xwdcompose.ui.CellColorHelper.colorForSelectionMode
import java.util.*

val sizedTo100 = Modifier.size(100.dp)

@Preview(showBackground = false)
@Composable
fun PreviewGame() {
    Box(modifier = Modifier.size(100.dp)) {
        GameTable(board = BoardData.board)
    }
}

@Composable
fun GameTable(board: Board) {
    BoxWithConstraints {
        val squareWidth = maxWidth / BoardData.board.edgeCount

        Box () {
            board.squares.chunked(board.edgeCount).forEach { row ->
                Row {
                    row.forEach { square ->
                        when (square.squareType) {
                            SquareType.LETTER -> {
                                LetterSquare(
                                    modifier = Modifier.size(squareWidth),
                                    square = square,
                                    onClick = {}
                                )
                            }
                            SquareType.BLACK -> BlackSquareBackground(
                                    modifier = Modifier.size(squareWidth),
                            )
                        }
                    }
                }
            }
        }
    }
}