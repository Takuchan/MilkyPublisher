package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.takuchan.milkypublisher.components.CameraPreview
import com.takuchan.milkypublisher.model.LogScreenData
import com.takuchan.milkypublisher.model.LogScreenEnum
import com.takuchan.milkypublisher.viewmodel.DetectState
import com.takuchan.milkypublisher.viewmodel.LogViewModel
import java.util.Date
import java.util.concurrent.ExecutorService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navMainController: NavController,
    navHomeController: NavController,
    modifier: Modifier = Modifier,
    detectState: DetectState,
    cameraExecutorService: ExecutorService,
    toBluetoothSettingButton: () -> Unit,
    logViewModel: LogViewModel
) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA,
    )



    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cameraPermissionState.status.isGranted) {

                CameraPreview(
                    cameraExecutorService = cameraExecutorService,
                    logViewModel = logViewModel
                )

            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                        "本アプリはカメラ機能を使います。パーミッション許可を行ってください"
                    } else {
                        "本アプリを使用するためにはカメラのパーミッション許可が必須です。"
                    }
                    Text(textToShow)
                    Button(onClick = {
                        cameraPermissionState.launchPermissionRequest()
                        logViewModel.addLogScreenList(LogScreenData(Date(),LogScreenEnum.Application.name,"認証しました"))
                    }) {
                        Text("パーミッション許可")
                    }
                }
            }
        }


    }


}

@Composable
fun SensorDataScreen() {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var accelerometerData by remember { mutableStateOf(emptyList<Triple<Float, Float, Float>>()) }
    var gyroscopeData by remember { mutableStateOf(emptyList<Triple<Float, Float, Float>>()) }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val (x, y, z) = event.values
                    accelerometerData = (accelerometerData + Triple(x, y, z)).takeLast(90)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    val (x, y, z) = event.values
                    gyroscopeData = (gyroscopeData + Triple(x, y, z)).takeLast(90)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
        sensorManager.registerListener(
            sensorEventListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Column {
        Text("Accelerometer Data")
        LineChart(
            data = accelerometerData,
            xLabel = "Time",
            yLabel = "Acceleration"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Gyroscope Data")
        LineChart(
            data = gyroscopeData,
            xLabel = "Time",
            yLabel = "Angular Velocity"
        )
    }
}

@Composable
fun LineChart(
    data: List<Triple<Float, Float, Float>>,
    xLabel: String,
    yLabel: String
) {
    val xValues = (0 until data.size).map { it.toFloat() }
    val yValuesX = data.map { it.first }
    val yValuesY = data.map { it.second }
    val yValuesZ = data.map { it.third }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(androidx.compose.ui.graphics.Color.White)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val xScale = size.width / (data.size - 1)
            val yScale = size.height / (data.maxOfOrNull { maxOf(it.first, it.second, it.third) } ?: 1f)

            drawLine(
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                color = androidx.compose.ui.graphics.Color.Blue,
                strokeWidth = 1.dp.toPx()
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    xLabel,
                    size.width / 2,
                    size.height - 16.dp.toPx(),
                    Paint().apply {
                        color = Color.BLACK
                        textSize = 14.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )

                drawText(
                    yLabel,
                    16.dp.toPx(),
                    size.height / 2,
                    Paint().apply {
                        color = Color.BLACK
                        textSize = 14.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        rotate(-90F)
                    }
                )
            }

            // Draw X line
            drawPoints(
                points = xValues.zip(yValuesX).map { (x, y) ->
                    Offset(x * xScale, size.height - y * yScale)
                },
                pointMode = PointMode.Polygon,
                color = androidx.compose.ui.graphics.Color.Red,
                strokeWidth = 2.dp.toPx()
            )

            // Draw Y line
            drawPoints(
                points = xValues.zip(yValuesY).map { (x, y) ->
                    Offset(x * xScale, size.height - y * yScale)
                },
                pointMode = PointMode.Polygon,
                color = androidx.compose.ui.graphics.Color.Green,
                strokeWidth = 2.dp.toPx()
            )

            // Draw Z line
            drawPoints(
                points = xValues.zip(yValuesZ).map { (x, y) ->
                    Offset(x * xScale, size.height - y * yScale)
                },
                pointMode = PointMode.Polygon,
                color = androidx.compose.ui.graphics.Color.Blue,
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

