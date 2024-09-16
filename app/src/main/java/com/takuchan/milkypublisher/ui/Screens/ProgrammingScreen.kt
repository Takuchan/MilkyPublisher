package com.takuchan.milkypublisher.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

// プログラムブロックを表すデータクラス
data class ProgramBlock(val id: Int, val name: String, val color: Color)

@Composable
fun ProgrammingScreen() {
    // 利用可能なブロックのリスト
    val availableBlocks = listOf(
        ProgramBlock(1, "移動", Color.Blue),
        ProgramBlock(2, "回転", Color.Green),
        ProgramBlock(3, "ジャンプ", Color.Yellow)
    )

    // プログラムとして組み立てられたブロックのリスト
    var programBlocks by remember { mutableStateOf(listOf<ProgramBlock>()) }

    // ドラッグ中のブロック
    var draggedBlock by remember { mutableStateOf<ProgramBlock?>(null) }

    Column {
        // 利用可能なブロック
        Text("利用可能なブロック:")
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(availableBlocks) { block ->
                BlockItem(block = block, onDragStart = { draggedBlock = it })
            }
        }

        // プログラムエリア
        Text("プログラム:")
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            items(programBlocks) { block ->
                BlockItem(block = block, isDraggable = false)
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.LightGray)
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                change.consume()
                                draggedBlock?.let {
                                    programBlocks = programBlocks + it
                                    draggedBlock = null
                                }
                            }
                        }
                ) {
                    Text("ブロックをここにドロップ")
                }
            }
        }

        // プログラムを保存するボタン
        Button(onClick = { saveProgramToArray(programBlocks) }) {
            Text("プログラムを保存")
        }
    }
}

@Composable
fun BlockItem(block: ProgramBlock, isDraggable: Boolean = true, onDragStart: (ProgramBlock) -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(block.color)
            .fillMaxWidth()
            .height(40.dp)
            .then(
                if (isDraggable) {
                    Modifier.pointerInput(block) {
                        detectDragGestures(
                            onDragStart = { onDragStart(block) }
                        ) { _, _ -> }
                    }
                } else {
                    Modifier
                }
            )
    ) {
        Text(block.name)
    }
}

fun saveProgramToArray(programBlocks: List<ProgramBlock>) {
    // プログラムブロックのIDを配列として保存
    val programArray = programBlocks.map { it.id }.toIntArray()
    // ここで programArray を使って保存処理を行う
    println("保存されたプログラム: ${programArray.contentToString()}")
}
