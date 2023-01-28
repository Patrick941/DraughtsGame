package com.example.draughtsgame

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class Player1CapturedClass : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(listItem)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.myTextView.text = "3"
    }

    override fun getItemCount(): Int {
        return 8
    }

}

open class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    val myTextView  = view.findViewById<TextView>(R.id.textView)
    //val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.circle, null)
    //val image: ImageView = view.findViewById(R.id.capturedPiece)

}