package com.takuchan.milkypublisher.compose

import android.util.Log

import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

import com.takuchan.milkypublisher.R
import com.takuchan.milkypublisher.viewmodel.ControllerViewModel


import androidx.compose.material3.Card
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.takuchan.milkypublisher.preference.TmpUDPData

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


//絶対必要
//緊急停止用ボタン
//バッテリー残量・電波強度
//ロボットが接続しているネットワーク状況
//PC接続済みのデバイス一覧
//PC接続しているカメラ画像
//
//ここで問題
//モニタリングツールなのか、操作ツールなのか
//https://www.irobot-jp.com/irobothomeapp.html　参考
//
//誰がどこでどのように使うのかを想定
//家庭用なのか産業用なのか
//
//
//https://www.youtube.com/watch?v=3zv3J3X1V5A
//TODO: 使用者の対象を想定する。年齢層や職種なども想定
//TODO: パソコン中級者の定義を決めておく。個人的な。




@Composable
fun ControllerScreen(
    onJoystickMove: (Float, Float) -> Unit,
    onActionPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val (message, setMessage) = remember { mutableStateOf("") }
    Card(
        modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 12.dp)
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                JoyStick(
                    modifier = Modifier.size(200.dp),
                    moved = onJoystickMove

                )
                Button(
                    onClick = {
                        TmpUDPData.floatx = 0.0f
                        TmpUDPData.floatY = 0.0f
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Text("緊急停止")
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxWidth()
            ) { snackbarData ->
                Snackbar(
                    modifier = Modifier.fillMaxWidth(),
                    action = {
                        Text(
                            "Dismiss",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    },
                ) {
                    Text("緊急停止しました。", color = Color.White)

                }
            }
        }
    }

}

@Composable
fun JoyStick(
    modifier: Modifier = Modifier,
    size: Dp = 170.dp,
    dotSize: Dp = 100.dp,
    backgroundImage: Int = R.drawable.joystick_background_1,
    dotImage: Int = R.drawable.joystick_dot_1,
    moved: (x: Float, y: Float) -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
    ) {
        val maxRadius = with(LocalDensity.current) { (size / 2).toPx() }
        val centerX = with(LocalDensity.current) { ((size - dotSize) / 2).toPx() }
        val centerY = with(LocalDensity.current) { ((size - dotSize) / 2).toPx() }

        var offsetX by remember { mutableStateOf(centerX) }
        var offsetY by remember { mutableStateOf(centerY) }

        var radius by remember { mutableStateOf(0f) }
        var theta by remember { mutableStateOf(0f) }

        var positionX by remember { mutableStateOf(0f) }
        var positionY by remember { mutableStateOf(0f) }

        Image(
            painterResource(id = backgroundImage),
            "JoyStickBackground",
            modifier = Modifier.size(size),
        )

        Image(
            painterResource(id = dotImage),
            "JoyStickDot",
            modifier = Modifier
                .offset {
                    IntOffset(
                        (positionX + centerX).roundToInt(),
                        (positionY + centerY).roundToInt()
                    )
                }
                .size(dotSize)
                .pointerInput(Unit) {
                    detectDragGestures(onDragEnd = {
                        offsetX = centerX
                        offsetY = centerY
                        radius = 0f
                        theta = 0f
                        positionX = 0f
                        positionY = 0f
                    }) { pointerInputChange: PointerInputChange, offset: Offset ->
                        val x = offsetX + offset.x - centerX
                        val y = offsetY + offset.y - centerY

                        pointerInputChange.consume()

                        theta = if (x >= 0 && y >= 0) {
                            atan(y / x)
                        } else if (x < 0 && y >= 0) {
                            (Math.PI).toFloat() + atan(y / x)
                        } else if (x < 0 && y < 0) {
                            -(Math.PI).toFloat() + atan(y / x)
                        } else {
                            atan(y / x)
                        }

                        radius = sqrt((x.pow(2)) + (y.pow(2)))

                        offsetX += offset.x
                        offsetY += offset.y

                        if (radius > maxRadius) {
                            polarToCartesian(maxRadius, theta)
                        } else {
                            polarToCartesian(radius, theta)
                        }.apply {
                            positionX = first
                            positionY = second
                        }
                    }
                }
                .onGloballyPositioned { coordinates ->
                    TmpUDPData.floatx = (coordinates.positionInParent().x - centerX) / maxRadius
                    TmpUDPData.floatY = -(coordinates.positionInParent().y - centerY) / maxRadius
                    Log.d("TMPUPD",TmpUDPData.floatY.toString())
                    moved(
                        (coordinates.positionInParent().x - centerX) / maxRadius,
                        -(coordinates.positionInParent().y - centerY) / maxRadius
                    )
                },
        )
    }
}

private fun polarToCartesian(radius: Float, theta: Float): Pair<Float, Float> =
    Pair(radius * cos(theta), radius * sin(theta))
@Composable
fun RobotControllerScreen() {
    var robotController: RobotController? = null

    LaunchedEffect(Unit) {
        robotController = RobotController()
    }

    ControllerScreen(
        onJoystickMove = { x, y ->
            robotController?.move(x, y)
        },
        onActionPressed = { robotController?.performAction() }
    )
}

class RobotController {
    fun move(x: Float, y: Float) {
        // ジョイスティックの入力に基づいてロボットを移動させるロジックを実装
    }

    fun performAction() {

    }
}