package com.takuchan.milkypublisher.compose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OthersPublisherScreen(

){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "トピックで送信するデータを選択してください")
//        AccelerometerSample()
        SensorScreen()

    }
}

@Composable
fun SensorScreen() {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val gyroscopeValue = remember { mutableStateOf("") }
    val pressureValue = remember { mutableStateOf("") }
    val temperatureValue = remember { mutableStateOf("") }
    val lightValue = remember { mutableStateOf("") }

    // Gyroscope sensor listener
    val gyroscopeListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val value = "X: ${it.values[0]}, Y: ${it.values[1]}, Z: ${it.values[2]}"
                gyroscopeValue.value = value
            }
        }
    }

    // Pressure sensor listener
    val pressureListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val value = "${it.values[0]} hPa"
                pressureValue.value = value
            }
        }
    }

    // Temperature sensor listener
    val temperatureListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val value = "${it.values[0]} °C"
                temperatureValue.value = value
            }
        }
    }

    // Light sensor listener
    val lightListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val value = "${it.values[0]} lx"
                lightValue.value = value
            }
        }
    }

    // Register sensor listeners
    DisposableEffect(Unit) {
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        val temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(pressureListener, pressure, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(temperatureListener, temperature, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(gyroscopeListener)
            sensorManager.unregisterListener(pressureListener)
            sensorManager.unregisterListener(temperatureListener)
            sensorManager.unregisterListener(lightListener)
        }
    }

    Column {
        Text(
            text = "Sensor Data",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        SensorGrid(
            gyroscopeValue = gyroscopeValue.value,
            pressureValue = pressureValue.value,
            temperatureValue = temperatureValue.value,
            lightValue = lightValue.value
        )
    }
}
@Composable
fun SensorGrid(
    gyroscopeValue: String,
    pressureValue: String,
    temperatureValue: String,
    lightValue: String
) {
    val sensorData = listOf(
        Pair("Gyroscope", gyroscopeValue),
        Pair("Pressure", pressureValue),
        Pair("Temperature", temperatureValue),
        Pair("Light", lightValue)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sensorData) { (sensorName, sensorValue) ->
            SensorItem(sensorName, sensorValue)
        }
    }
}

@Composable
fun SensorItem(sensorName: String, sensorValue: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = sensorName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = sensorValue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

