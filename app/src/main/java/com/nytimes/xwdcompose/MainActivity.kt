package com.nytimes.xwdcompose

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import com.nytimes.xwdcompose.data.BoardData
import com.nytimes.xwdcompose.ui.GameBoard
import com.nytimes.xwdcompose.ui.XWDComposeTheme
import com.nytimes.xwdcompose.viewmodel.BoardViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import com.nytimes.xwdcompose.data.Square
import com.nytimes.xwdcompose.ui.ClueBar
import com.nytimes.xwdcompose.viewmodel.BoardState


class MainActivity : AppCompatActivity() {

    private val viewModel: BoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XWDComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column (modifier = Modifier.fillMaxHeight()) {
                        Box (modifier = Modifier.fillMaxWidth()) {
                            Text("Score: 100")
                        }
                        viewModel.boardState?.let {boardState ->
                            GameBoard(boardState.squares,
                                viewModel::selectSquare)
                            ClueBar(clue = boardState.selectedClue)
                        }
                        Button(onClick = {viewModel.checkBoard()}){
                            Text(text ="Check Your Work")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CustomKeyboard {
                                viewModel.enterLetter(it)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, keyEvent: KeyEvent): Boolean {
        if (keyCode in 9..54) { // alphanumeric
            viewModel.enterLetter(keyEvent.unicodeChar.toChar().toString())
            return true
        }
        return super.onKeyDown(keyCode, keyEvent)
    }
}

@Suppress("DEPRECATION")
@Composable
fun CustomKeyboard(onKeyClicked: (String) -> Unit) {
    val context = LocalContext.current
    val keyboardView = KeyboardView(context, null)
    keyboardView.keyboard = Keyboard(context, R.xml.keyboard)

    AndroidView({ keyboardView }) { keyboard ->
        keyboard.setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
            override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
                onKeyClicked(primaryCode.toChar().toString())
            }

            override fun onPress(primaryCode: Int) = Unit
            override fun onRelease(primaryCode: Int) = Unit
            override fun onText(text: CharSequence?) = Unit
            override fun swipeLeft() = Unit
            override fun swipeRight() = Unit
            override fun swipeDown() = Unit
            override fun swipeUp() = Unit
        })
    }
}