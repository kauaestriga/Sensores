package com.example.sensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FlippedDetectionActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var gravitySensor: Sensor
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flipped_detection)

        mediaPlayer = MediaPlayer.create(this, R.raw.super_mario_theme);
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null) {
            Log.i("SENSOR", "SENSOR DE GRAVIDADE INDISPONÍVEL")
        } else {
            gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let {
            if (p0.values[2] < -9.7) {
                if (mediaPlayer.isPlaying) mediaPlayer.pause()
            } else {
                if (!mediaPlayer.isPlaying) mediaPlayer.start()
            }
        }
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    override fun onResume() {
        super.onResume()
        if (this::gravitySensor.isInitialized) sensorManager.registerListener(
            this,
            gravitySensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }
}