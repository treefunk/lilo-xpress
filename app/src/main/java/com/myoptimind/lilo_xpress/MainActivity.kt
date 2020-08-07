package com.myoptimind.lilo_xpress


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash_screen)

        lifecycleScope.launch {
            delay(3000)
            setContentView(R.layout.activity_main)
        }

    }
}