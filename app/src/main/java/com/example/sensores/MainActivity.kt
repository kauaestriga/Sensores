package com.example.sensores

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private lateinit var tempSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null)
            Log.i("SENSOR", "SENSOR INDISPONÍVEL")
        else
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        btFlippedDetection.setOnClickListener {
            startActivity(Intent(this, FlippedDetectionActivity::class.java))
        }

        btShake.setOnClickListener {
            startActivity(Intent(this, ShakeActivity::class.java))
        }

        btStepCounter.setOnClickListener {
            startActivity(Intent(this, StepCounter::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
            sensorManager.unregisterListener(this)
    }

    private fun printSensors() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for(sensor in sensorList) {
            Log.i("SENSOR: ", "Nome: ${sensor.name} - Tipo ${sensor.type} -${sensor.stringType} ")
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let {
            Log.i("SENSOR", it.values[0].toString())
            tvTempeture.text = "Temperatura: ${it.values[0].toString()}"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}