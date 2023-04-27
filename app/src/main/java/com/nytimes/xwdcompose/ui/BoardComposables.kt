package com.nytimes.xwdcompose.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nytimes.xwdcompose.data.BoardData
import com.nytimes.xwdcompose.data.Clue
import com.nytimes.xwdcompose.data.Square
import com.nytimes.xwdcompose.data.SquareType
import kotlin.math.sqrt

val CELL_BORDER_WIDTH = 1.dp

@Composable
fun GameBoard(squares: List<Square>,
              squareOnClick: (Square) -> Unit) {

    val edgeCount = sqrt(squares.size.toDouble()).toInt()

    BoxWithConstraints {
        val squareWidth = maxWidth / BoardData.board.edgeCount

        Box{
            OuterHorizontalBorder()

            Column {
                squares.chunked(edgeCount).withIndex().forEach { (rowIndex, row) ->
                    Box {
                        Row {
                            row.withIndex().forEach { (squareIndex, square) ->
                                when (square.squareType) {
                                    SquareType.LETTER -> {
                                        LetterSquare(
                                            modifier = Modifier
                                                .width(squareWidth)
                                                .height(squareWidth),
                                            square = square,
                                            onClick = {
                                                squareOnClick(it)
                                            })
                                    }
                                    SquareType.BLACK -> {
                                        BlackSquareBackground(
                                            modifier = Modifier.size(squareWidth),
                                        )
                                    }
                                }
                                when (squareIndex + 1) {
                                    edgeCount -> OuterVerticalBorder(squareWidth)
                                    else -> InnerVerticalBorder(squareWidth)
                                }
                            }
                        }
                    }
                    when (rowIndex + 1) {
                        edgeCount -> OuterHorizontalBorder()
                        else -> InnerHorizontalBorder()
                    }
                }
            }
        }
    }
}

@Composable
fun InnerVerticalBorder(borderHeight: Dp) {
    Box(
        modifier = Modifier
            .width(CELL_BORDER_WIDTH)
            .height(borderHeight)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            ))
}

@Composable
fun OuterVerticalBorder(squareWidth: Dp) {
    Column {
        Box(modifier = Modifier
            .width(CELL_BORDER_WIDTH)
            .height(squareWidth)
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            ))
    }
}

@Composable
fun OuterHorizontalBorder() {
    Row {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(CELL_BORDER_WIDTH)
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            ))
    }
}

@Composable
fun InnerHorizontalBorder() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(CELL_BORDER_WIDTH)
        .background(
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        ))
}

@Composable
fun ClueBar(clue: Clue) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(red = 0xA7, green = 0xD8, blue = 0xFF)) {
        Text(clue.clueText, color = Color.Black, modifier = Modifier.padding(10.dp))
    }
}


