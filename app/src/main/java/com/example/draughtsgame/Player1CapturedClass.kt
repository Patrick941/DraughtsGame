package com.example.draughtsgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Player1CapturedClass : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.List_Item, parent, false)
        return MyViewHolder(listItem)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 1
    }

}

open class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val myTextView = view.findViewById<TextView>(R.id.tvName)
}