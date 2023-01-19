package com.certeixeira.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Teste", "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i("Teste", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i("Teste", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("Teste", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i("Teste", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Teste", "onDestroy")

    }

}