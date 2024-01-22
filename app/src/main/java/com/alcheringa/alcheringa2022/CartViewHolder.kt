package com.alcheringa.alcheringa2022

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

//this is the old one for Cart Items

class CartViewHolder(itemView: View, var onItemClick: onItemClick) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var name: TextView
    var type: TextView
    var price: TextView
    var size: TextView
    var imageView: ImageView
    var remove_btn: TextView? = null
    var count: TextView
    var card: ConstraintLayout
    var increment: ImageView
    var decrement: ImageView

    init {
        name = itemView.findViewById(R.id.hoodie_name)
        type = itemView.findViewById(R.id.type)
        price = itemView.findViewById(R.id.price)
        size = itemView.findViewById(R.id.size_2)
        imageView = itemView.findViewById(R.id.merch_image)
        //        remove_btn=itemView.findViewById(R.id.remove_btn);
        count = itemView.findViewById(R.id.quantity)
        card = itemView.findViewById(R.id.bg_card)
        increment = itemView.findViewById(R.id.add)
        decrement = itemView.findViewById(R.id.subtract)
        //        remove_btn.setOnClickListener(this);
        increment.setOnClickListener(this)
        decrement.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        onItemClick.OnAnyClick(adapterPosition)
        when (v.id) {
            R.id.add -> onItemClick.OnIncrementClick(adapterPosition)
            R.id.subtract -> onItemClick.OnDecrementClick(adapterPosition)
        }
    }
}