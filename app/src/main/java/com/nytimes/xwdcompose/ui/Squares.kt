package com.nytimes.xwdcompose.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nytimes.xwdcompose.data.BoardData.board
import com.nytimes.xwdcompose.data.SelectionMode
import com.nytimes.xwdcompose.data.Square
import com.nytimes.xwdcompose.data.SquareType

@Composable
fun StrikeOut() {
    val strokeWidth = with(LocalDensity.current) {
        5.dp.toPx()
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(Color.Red, Offset(this.size.width, 0f),
                Offset(0f, this.size.height),
                strokeWidth = strokeWidth)
    }
}



@Composable
fun LetterSquare(modifier: Modifier = Modifier.fillMaxSize(),
                 square: Square,
                 onClick: (Square) -> Unit) {

    Box(modifier = modifier.clickable(onClick = {onClick(square)})) {
        BoxWithConstraints {
            val textSize = with(LocalDensity.current) {
                (maxWidth.toPx() * .6f).toSp()
            }

            val numberSize = with(LocalDensity.current) {
                (maxWidth.toPx() * .15f).toSp()
            }

            Box {
                var selectMode: SelectionMode = SelectionMode.NONE
                square.userAnswer?.let { userAnswer ->
                    if (square.checked && userAnswer != square.answer) {
                        StrikeOut()
                        selectMode = square.selectionMode
                    } else if(square.checked && userAnswer == square.answer) {
                        selectMode = SelectionMode.CORRECT
                    }
                }

                ColoredSquareBackground(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(CellColorHelper.colorForSelectionMode(selectMode))
                                //.clip(shape = RectangleShape)
                                .align(Alignment.Center))

                square.userAnswer?.let { userAnswer ->
                    Text(text = userAnswer,
                            style = TextStyle(fontSize = textSize),
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                    )
                }

                square.cellNumber?.let {
                    Text(text = it,
                            style = TextStyle(fontSize = numberSize),
                            color = Color.DarkGray,
                            modifier = Modifier.align(Alignment.TopStart)
                                    .padding(3.dp))
                }

                square.userAnswer?.let { userAnswer ->
                    if (square.checked && userAnswer != square.answer) {
                        StrikeOut()
                    }
                }
            }
        }
    }
}


@Composable
fun ColoredSquareBackground(
        modifier: Modifier) {
    Box(
            modifier = modifier
    )
}

@Composable
fun BlackSquareBackground(modifier: Modifier = Modifier.fillMaxSize().background(CellBackground.BLACK_SQUARE.color)) {
    ColoredSquareBackground(
            modifier = modifier)
}



enum class CellBackground constructor(val color: Color) {
    NONE(Color.White),
    CURSOR(Color(red = 0xFF, green = 0xDA, blue = 0x00)),
    ACTIVE_CLUE(Color(red = 0xA7, green = 0xD8, blue = 0xFF)),
    CORRECT(Color.Green),
    BLACK_SQUARE(Color.Black)
}

object CellColorHelper {
    fun colorForSelectionMode(mode: SelectionMode) = when(mode) {
        SelectionMode.NONE -> CellBackground.NONE.color
        SelectionMode.CURSOR -> CellBackground.CURSOR.color
        SelectionMode.ACTIVE_CLUE -> CellBackground.ACTIVE_CLUE.color
        SelectionMode.CORRECT -> CellBackground.CORRECT.color
    }
}