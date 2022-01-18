package com.rbc.yelp.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rbc.yelp.R

class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val header = itemView.findViewById<TextView>(R.id.headerTv)
}