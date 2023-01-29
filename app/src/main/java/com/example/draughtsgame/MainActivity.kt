package com.example.draughtsgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var thisName = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val squares = arrayOf(findViewById<Button>(R.id.a1button), findViewById(R.id.a2button), findViewById(R.id.a3button), findViewById(R.id.a4button), findViewById(R.id.a5button), findViewById(R.id.a6button), findViewById(R.id.a7button), findViewById(R.id.a8button), findViewById(R.id.b1button), findViewById(R.id.b2button), findViewById(R.id.b3button), findViewById(R.id.b4button), findViewById(R.id.b5button), findViewById(R.id.b6button), findViewById(R.id.b7button), findViewById(R.id.b8button), findViewById(R.id.c1button), findViewById(R.id.c2button), findViewById(R.id.c3button), findViewById(R.id.c4button), findViewById(R.id.c5button), findViewById(R.id.c6button), findViewById(R.id.c7button), findViewById(R.id.c8button), findViewById(R.id.d1button), findViewById(R.id.d2button), findViewById(R.id.d3button), findViewById(R.id.d4button), findViewById(R.id.d5button), findViewById(R.id.d6button), findViewById(R.id.d7button), findViewById(R.id.d8button), findViewById(R.id.e1button),
            findViewById(R.id.e2button), findViewById(R.id.e2button), findViewById(R.id.e3button), findViewById(R.id.e4button), findViewById(R.id.e5button), findViewById(R.id.e6button), findViewById(R.id.e7button), findViewById(R.id.e8button), findViewById(R.id.f1button), findViewById(R.id.f2button), findViewById(R.id.f2button), findViewById(R.id.f3button), findViewById(R.id.f4button), findViewById(R.id.f5button), findViewById(R.id.f6button), findViewById(R.id.f7button), findViewById(R.id.f8button),
            findViewById(R.id.g1button), findViewById(R.id.g2button), findViewById(R.id.g3button), findViewById(R.id.g4button), findViewById(R.id.g5button), findViewById(R.id.g6button), findViewById(R.id.g7button), findViewById(R.id.g8button), findViewById(R.id.h1button), findViewById(R.id.h2button), findViewById(R.id.h3button), findViewById(R.id.h4button), findViewById(R.id.h5button), findViewById(R.id.h6button), findViewById(R.id.h7button), findViewById(R.id.h8button))
            CoroutineScope(Dispatchers.Main).launch {
                squares.forEach {
                    it.setBackgroundColor(Color.TRANSPARENT)
                    it.setOnClickListener {
                        Log.i("myTag", "$it was pressed in $thisName")
                    }
                }
            }
        Log.i("myTag", "creating $thisName")

        val letterCodes = findViewById<RecyclerView>(R.id.letterCodes)
        //player1CapturedRecycler.setBackgroundColor()
        letterCodes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        letterCodes.adapter = LetterCodes()

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