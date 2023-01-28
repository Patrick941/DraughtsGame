package com.example.draughtsgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var thisName = "MainActivity"
    private val Player1CapturedRecycler = findViewById<RecyclerView>(R.id.Player1Captured)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("myTag", "creating $thisName")
        Player1CapturedRecycler.setBackgroundColor(Color.YELLOW )
        Player1CapturedRecycler.layoutManager = LinearLayoutManager(this)
        Player1CapturedRecycler.adapter = Player1CapturedClass()

    }
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            //controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onResume(){
        super.onResume()
        hideSystemUI()
        Log.i("myTag", "resuming $thisName")
    }

    override fun onStart(){
        super.onStart()
        Log.i("myTag", "starting $thisName")
    }

    override fun onStop(){
        super.onStop()
        Log.i("myTag", "stopping $thisName")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("myTag", "restarting $thisName")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("myTag", "destroying $thisName")
    }
}