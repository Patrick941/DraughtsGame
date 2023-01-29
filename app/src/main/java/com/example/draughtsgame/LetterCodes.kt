package com.example.draughtsgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LetterCodes : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.letter_box, parent, false)
        return MyViewHolder(listItem)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var posAscii = position.digitToChar()
        val distanceFromH = 14 - (2 * position)
        posAscii+=(42 + distanceFromH)
        holder.myTextView.text = "$posAscii"
    }

    override fun getItemCount(): Int {
        return 8
    }

}

open class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    val myTextView  = view.findViewById<TextView>(R.id.textView)
    //val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.dark_piece.xml, null)
    //val image: ImageView = view.findViewById(R.id.capturedPiece)

}