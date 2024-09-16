package com.takuchan.milkypublisher.Model

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also {  future ->
            future.addListener({
                continuation.resume(future.get())
            },executor)
        }
    }

/*
Context.executorでAndroidアプリケーションのメインスレッド上でタスクを実行するためのExecutor
を取得するメソッドである。
 */
val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)
