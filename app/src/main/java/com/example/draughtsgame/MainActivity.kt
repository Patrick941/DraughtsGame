package com.example.draughtsgame

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.prefs.BackingStoreException


class MainActivity : AppCompatActivity() {
    private var thisName = "MainActivity"
    private val lightSquares = arrayOf(1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1)
    private val lightSquareStates = arrayOf(1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)
    private val darkSquareStates = arrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1)
    private val crownStates = arrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1)
    private var currentSquare = - 1
    private var currentTurn = 0
    private var allCurrentBorder = arrayOf(-1, -1, -1, -1)
    private var jumpableSquares = arrayOf(-1, -1, -1, -1)
    private var currentPieceState = 0
    private var firstMove = 1
    private var keepGoing = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            for(i in 0 .. 63){
                crownStates[i] = 0
                var crown = (KingCrowns).getChildAt(i)
                crown.visibility = INVISIBLE
            }
            val squares = arrayOf(findViewById<Button>(R.id.h1button), findViewById(R.id.g1button), findViewById(R.id.f1button), findViewById(R.id.e1button), findViewById(R.id.d1button), findViewById(R.id.c1button), findViewById(R.id.b1button), findViewById(R.id.a1button), findViewById(R.id.h2button), findViewById(R.id.g2button), findViewById(R.id.f2button), findViewById(R.id.e2button), findViewById(R.id.d2button), findViewById(R.id.c2button), findViewById(R.id.b2button), findViewById(R.id.a2button), findViewById(R.id.h3button), findViewById(R.id.g3button), findViewById(R.id.f3button), findViewById(R.id.e3button), findViewById(R.id.d3button), findViewById(R.id.c3button), findViewById(R.id.b3button), findViewById(R.id.a3button), findViewById(R.id.h4button), findViewById(R.id.g4button), findViewById(R.id.f4button), findViewById(R.id.e4button), findViewById(R.id.d4button), findViewById(R.id.c4button), findViewById(R.id.b4button), findViewById(R.id.a4button), findViewById(R.id.h5button), findViewById(R.id.g5button), findViewById(R.id.f5button), findViewById(R.id.e5button), findViewById(R.id.d5button), findViewById(R.id.c5button), findViewById(R.id.b5button), findViewById(R.id.a5button), findViewById(R.id.h6button), findViewById(R.id.g6button), findViewById(R.id.f6button), findViewById(R.id.e6button), findViewById(R.id.d6button), findViewById(R.id.c6button), findViewById(R.id.b6button), findViewById(R.id.a6button), findViewById(R.id.h7button), findViewById(R.id.g7button), findViewById(R.id.f7button), findViewById(R.id.e7button), findViewById(R.id.d7button), findViewById(R.id.c7button), findViewById(R.id.b7button), findViewById(R.id.a7button), findViewById(R.id.h8button), findViewById(R.id.g8button), findViewById(R.id.f8button), findViewById(R.id.e8button), findViewById(R.id.d8button), findViewById(R.id.c8button), findViewById(R.id.b8button), findViewById(R.id.a8button))
            val player1Name = findViewById<TextView>(R.id.Player1)
            val player2Name = findViewById<TextView>(R.id.Player2)
            player1Name.setBackgroundColor(Color.TRANSPARENT)
            player2Name.setBackgroundColor(Color.GREEN)
                for (index in 0..63) {
                    val it = squares[index]
                    it.setBackgroundColor(Color.TRANSPARENT)
                    it.setOnClickListener {
                        if(lightSquares[index] == 0) {
                            for (i in 0..3) {
                                if (index == allCurrentBorder[i] && index != jumpableSquares[i]) {
                                    if (currentTurn == 1) {
                                        var nextChild = (LightPieces).getChildAt(currentSquare)
                                        nextChild.visibility = INVISIBLE
                                        nextChild = (LightPieces).getChildAt(allCurrentBorder[i])
                                        nextChild.visibility = VISIBLE
                                        lightSquareStates[allCurrentBorder[i]] = 0
                                        lightSquareStates[currentSquare] = 1
                                        currentTurn = 0
                                        player1Name.setBackgroundColor(Color.TRANSPARENT)
                                        player2Name.setBackgroundColor(Color.GREEN)
                                        Log.i("myTag", "$currentSquare moved to tile ${allCurrentBorder[i]}")
                                        if(allCurrentBorder[i] >= 55){
                                            Log.i("myTag", "light has unlocked a king")
                                            crownStates[allCurrentBorder[i]] = 1
                                            var kingCrown = (KingCrowns).getChildAt(allCurrentBorder[i])
                                            kingCrown.visibility = VISIBLE
                                        }
                                        clearBorders()
                                    } else {
                                        var nextChild = (DarkPieces).getChildAt(currentSquare)
                                        nextChild.visibility = INVISIBLE
                                        nextChild = (DarkPieces).getChildAt(allCurrentBorder[i])
                                        nextChild.visibility = VISIBLE
                                        darkSquareStates[allCurrentBorder[i]] = 0
                                        darkSquareStates[currentSquare] = 1
                                        currentTurn = 1
                                        player1Name.setBackgroundColor(Color.GREEN)
                                        player2Name.setBackgroundColor(Color.TRANSPARENT)
                                        Log.i("myTag", "$currentSquare moved to tile ${allCurrentBorder[i]}")
                                        if(allCurrentBorder[i] <= 7){

                                        }
                                        clearBorders()
                                    }
                                    currentSquare = -1
                                } else if(index == jumpableSquares[i]){
                                    if (currentTurn == 1) {
                                        var nextChild = (LightPieces).getChildAt(currentSquare)
                                        nextChild.visibility = INVISIBLE
                                        nextChild = (LightPieces).getChildAt(jumpableSquares[i])
                                        nextChild.visibility = VISIBLE
                                        nextChild = (DarkPieces).getChildAt(allCurrentBorder[i])
                                        nextChild.visibility = INVISIBLE
                                        lightSquareStates[jumpableSquares[i]] = 0
                                        lightSquareStates[currentSquare] = 1
                                        darkSquareStates[allCurrentBorder[i]] = 1
                                        lightSquareStates[allCurrentBorder[i]] = 1
                                        if(jumpableSquares[i] >= 55){
                                            Log.i("myTag", "light has unlocked a king")
                                        }
                                        Log.i("myTag", "$currentSquare jumped over ${allCurrentBorder[i]} to ${jumpableSquares[i]}")
                                        if(doubleJumpTest(i)){
                                            for (i in 0..63) {
                                                squares[i].setBackgroundColor(Color.TRANSPARENT)
                                            }
                                            Log.i(
                                                "myTag",
                                                "Light can perform a double jump, the double jump squares are ${jumpableSquares[0]}, ${jumpableSquares[1]}, ${jumpableSquares[2]} and ${jumpableSquares[3]}"
                                            )
                                            keepGoing = 1
                                            for(j in 0.. 3){
                                                if(jumpableSquares[i] != -1) {
                                                    squares[jumpableSquares[i]].setBackgroundColor(Color.BLUE)

                                                }
                                            }
                                        } else {
                                            keepGoing = 0
                                        }
                                        if(keepGoing == 0) {
                                            clearBorders()
                                            currentTurn = 0
                                            player1Name.setBackgroundColor(Color.TRANSPARENT)
                                            player2Name.setBackgroundColor(Color.GREEN)
                                        }
                                    } else {
                                        var nextChild = (DarkPieces).getChildAt(currentSquare)
                                        nextChild.visibility = INVISIBLE
                                        nextChild = (DarkPieces).getChildAt(jumpableSquares[i])
                                        nextChild.visibility = VISIBLE
                                        nextChild = (LightPieces).getChildAt(allCurrentBorder[i])
                                        nextChild.visibility = INVISIBLE
                                        darkSquareStates[jumpableSquares[i]] = 0
                                        darkSquareStates[currentSquare] = 1
                                        lightSquareStates[allCurrentBorder[i]] = 1
                                        darkSquareStates[allCurrentBorder[i]] = 1
                                        Log.i("myTag", "$currentSquare jumped over ${allCurrentBorder[i]} to  ${jumpableSquares[i]}")
                                        if(jumpableSquares[i] <= 7){
                                            Log.i("myTag", "dark has unlocked a king")
                                        }
                                        if(doubleJumpTest(i)){
                                            Log.i("myTag", "Dark can perform a double jump, the double jump squares are ${jumpableSquares[0]}, ${jumpableSquares[1]}, ${jumpableSquares[2]} and ${jumpableSquares[3]}")
                                            keepGoing = 1
                                            for (i in 0..63) {
                                                squares[i].setBackgroundColor(Color.TRANSPARENT)
                                            }
                                            for(j in 0.. 3){
                                                if(jumpableSquares[i] != -1) {
                                                    squares[jumpableSquares[i]].setBackgroundColor(Color.BLUE)
                                                }
                                            }
                                        } else {
                                            keepGoing = 0
                                        }
                                        if(keepGoing == 0) {
                                            clearBorders()
                                            currentTurn = 1
                                            player1Name.setBackgroundColor(Color.GREEN)
                                            player2Name.setBackgroundColor(Color.TRANSPARENT)
                                        }
                                    }
                                    currentSquare = -1
                                }
                            }
                            currentSquare = index
                            if(keepGoing == 0) {
                                for (i in 0..63) {
                                    squares[i].setBackgroundColor(Color.TRANSPARENT)
                                }
                            } else {
                                for (i in 0..3) {
                                    if (jumpableSquares[i] in 0..63) {
                                        squares[jumpableSquares[i]].setBackgroundColor(Color.BLUE)
                                    }
                                }
                                Log.i("myTag", "keepingGoing the jumpable Squares are ${jumpableSquares[0]}, ${jumpableSquares[1]}, ${jumpableSquares[2]} and ${jumpableSquares[3]}")
                            }
                            if ((currentSquare != -1 || firstMove == 1) && keepGoing == 0) {
                                if((currentTurn == 1 && lightSquareStates[index] == 0) || (currentTurn == 0 && darkSquareStates[index] == 0)){
                                        firstMove = 0
                                        if(currentPieceState == 1){
                                            allCurrentBorder[0] = index - 9
                                            allCurrentBorder[1] = index - 7
                                            allCurrentBorder[2] = index + 9
                                            allCurrentBorder[3] = index + 7
                                        }else {
                                            if (currentTurn == 0) {
                                                allCurrentBorder[0] = index - 9
                                                allCurrentBorder[1] = index - 7
                                            } else {
                                                allCurrentBorder[2] = index + 9
                                                allCurrentBorder[3] = index + 7
                                            }
                                        }
                                        //0 is upleft   1 is upright    2 is downright  3 is downleft
                                        for (i in 0..3) {
                                            if (allCurrentBorder[i] in 0..63) {
                                                if(currentTurn == 1){
                                                    printBorders()
                                                }
                                                if(lightSquareStates[allCurrentBorder[i]] == 1 && darkSquareStates[allCurrentBorder[i]] == 1 && lightSquares[allCurrentBorder[i]] == 0) {
                                                    squares[allCurrentBorder[i]].setBackgroundColor(
                                                        Color.BLUE
                                                    )
                                                } else {
                                                    if(currentTurn == 0 && darkSquareStates[allCurrentBorder[i]] == 1){
                                                        val nextSquare = allCurrentBorder[i]
                                                        val nextBorder = arrayOf(nextSquare - 9, nextSquare - 7, nextSquare + 9, nextSquare + 7)
                                                        val jumpableSquare = nextBorder[i]
                                                        Log.i("myTag", "dark searching for jump")
                                                        if(jumpableSquare in 0..63) {
                                                            if (lightSquareStates[jumpableSquare] == 1 && darkSquareStates[jumpableSquare] == 1 && lightSquares[jumpableSquare] == 0) {
                                                                squares[nextBorder[i]].setBackgroundColor(
                                                                    Color.BLUE
                                                                )
                                                                jumpableSquares[i] = jumpableSquare
                                                                Log.i("myTag", "dark can jump")
                                                            }
                                                        }
                                                    } else if(currentTurn == 1 && lightSquareStates[allCurrentBorder[i]] == 1){
                                                        Log.i("myTag", "")
                                                        val nextSquare = allCurrentBorder[i]
                                                        val nextBorder = arrayOf(nextSquare - 9, nextSquare - 7, nextSquare + 9, nextSquare + 7)
                                                        val jumpableSquare = nextBorder[i]
                                                        Log.i("myTag", "light searching for jump")
                                                        if(jumpableSquare in 0..63){
                                                            if (lightSquareStates[jumpableSquare] == 1 && darkSquareStates[jumpableSquare] == 1 && lightSquares[jumpableSquare] == 0) {
                                                                squares[nextBorder[i]].setBackgroundColor(
                                                                    Color.BLUE
                                                                )
                                                                jumpableSquares[i] = jumpableSquare
                                                                Log.i("myTag", "light can jump")
                                                            }
                                                        }
                                                    }
                                                    if(jumpableSquares[i] == -1){
                                                        allCurrentBorder[i] = -1
                                                    }
                                                }
                                            }
                                        }
                                    }
                            }
                            currentSquare = index
                        }else {

                        }
                    }
                    //for debugging purposes
                    /*for (index in 0..63) {
                        if (lightSquareStates[index] == 1) {
                            val nextChild = (LightPieces).getChildAt(index)
                            nextChild.visibility = INVISIBLE
                        } else {
                            val nextChild = (LightPieces).getChildAt(index)
                            nextChild.visibility = VISIBLE
                        }
                        if (darkSquareStates[index] == 1) {
                            val nextChild = (DarkPieces).getChildAt(index)
                            nextChild.visibility = INVISIBLE
                        } else {
                            val nextChild = (DarkPieces).getChildAt(index)
                            nextChild.visibility = VISIBLE
                        }
                    }*/
                }
        Log.i("myTag", "creating $thisName")

        for (index in 0..63) {
            if (index < lightSquareStates.size) {
                if (lightSquareStates[index] == 1) {
                    val nextChild = (LightPieces).getChildAt(index)
                    nextChild.visibility = INVISIBLE
                }
            }
        }

        for (index in 0..63) {
            if (index < darkSquareStates.size) {
                if (darkSquareStates[index] == 1) {
                    val nextChild = (DarkPieces).getChildAt(index)
                    nextChild.visibility = INVISIBLE
                }
            }
        }

        val letterCodes = findViewById<RecyclerView>(R.id.letterCodes)
        //player1CapturedRecycler.setBackgroundColor()
        letterCodes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        letterCodes.adapter = LetterCodes()

    }

    private fun doubleJumpTest(index: Int): Boolean {
        var returnVal = false
        val testingForJump = jumpableSquares[index]
        Log.i("myTag", "Testing for double jumps at $testingForJump")
        jumpableSquares[0] = -1
        jumpableSquares[1] = -1
        jumpableSquares[2] = -1
        jumpableSquares[3] = -1
        if(currentPieceState == 1){
            allCurrentBorder[0] = testingForJump - 9
            allCurrentBorder[1] = testingForJump - 7
            allCurrentBorder[2] = testingForJump + 9
            allCurrentBorder[3] = testingForJump + 7
        }else {
            if (currentTurn == 0) {
                allCurrentBorder[0] = testingForJump - 9
                allCurrentBorder[1] = testingForJump - 7
                allCurrentBorder[2] = -1
                allCurrentBorder[3] = -1
            } else {
                allCurrentBorder[2] = testingForJump + 9
                allCurrentBorder[3] = testingForJump + 7
                allCurrentBorder[0] = -1
                allCurrentBorder[1] = -1
            }
        }
        for(i in 0..3) {
            if(allCurrentBorder[i] in 0..63) {
                if (currentTurn == 0 && darkSquareStates[allCurrentBorder[i]] == 1) {
                    val nextSquare = allCurrentBorder[i]
                    val nextBorder =
                        arrayOf(nextSquare - 9, nextSquare - 7, nextSquare + 9, nextSquare + 7)
                    val jumpableSquare = nextBorder[i]
                    Log.i("myTag", "dark searching for double jump over $nextSquare")
                    if (jumpableSquare in 0..63) {
                        if (lightSquareStates[jumpableSquare] == 1 && darkSquareStates[jumpableSquare] == 1 && lightSquares[jumpableSquare] == 0) {
                            jumpableSquares[i] = jumpableSquare
                            //Log.i("myTag", "dark can jump")
                            returnVal = true
                        }
                    }
                } else if (currentTurn == 1 && lightSquareStates[allCurrentBorder[i]] == 1) {
                    Log.i("myTag", "")
                    val nextSquare = allCurrentBorder[i]
                    val nextBorder =
                        arrayOf(nextSquare - 9, nextSquare - 7, nextSquare + 9, nextSquare + 7)
                    val jumpableSquare = nextBorder[i]
                    Log.i("myTag", "light searching for double jump over $nextSquare")
                    if (jumpableSquare in 0..63) {
                        if (lightSquareStates[jumpableSquare] == 1 && darkSquareStates[jumpableSquare] == 1 && lightSquares[jumpableSquare] == 0) {
                            jumpableSquares[i] = jumpableSquare
                            //Log.i("myTag", "light can jump")
                            returnVal = true
                        }
                    }
                }
            } else {
                Log.i("myTag", "${allCurrentBorder[i]} is out of bounds, not searching for double jump")
            }
        }
        Log.i("myTag", "Double jump not possible")
        return returnVal
    }

    private fun clearBorders() {
        allCurrentBorder[0] = -1
        allCurrentBorder[1] = -1
        allCurrentBorder[2] = -1
        allCurrentBorder[3] = -1
        jumpableSquares[0] = -1
        jumpableSquares[1] = -1
        jumpableSquares[2] = -1
        jumpableSquares[3] = -1
    }

    //debugging purposes
    private fun printBorders(){
        Log.i("myTag", "Borders are ${allCurrentBorder[0]}, ${allCurrentBorder[1]}, ${allCurrentBorder[2]} and ${allCurrentBorder[3]} the current available jumpable squares are ${jumpableSquares[0]}," +
                "${jumpableSquares[1]}, ${jumpableSquares[2]} and ${jumpableSquares[3]} while the current square is $currentSquare ")
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