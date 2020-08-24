package com.myoptimind.lilo_xpress


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash_screen)

        job = lifecycleScope.launchWhenResumed {
            switchScreen()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    suspend fun switchScreen(){
        for (i in 1..3){
            Log.v("Splashscreen", "${i}.. before switch")
            delay(1000)
        }
        setContentView(R.layout.activity_main)
    }


}