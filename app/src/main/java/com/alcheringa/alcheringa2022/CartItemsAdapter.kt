package com.alcheringa.alcheringa2022

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alcheringa.alcheringa2022.Model.cartModel
import com.bumptech.glide.Glide

//this is the old one for Cart Items
class CartItemsAdapter(
    var cartModelList: List<cartModel>,
    var onItemClick: onItemClick,
    var context: Context
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.count.text = cartModelList[position].count
        holder.name.text = cartModelList[position].name
        holder.price.text = "Rs. " + cartModelList[position].price + "."
        holder.type.text = cartModelList[position].type
        val size = cartModelList[position].size
        /*        if(Objects.equals(size, "S")){
            size="Small";
        }
        else  if(Objects.equals(size, "M")){
            size="Medium";
        }
        else  if(Objects.equals(size, "L")){
            size="Large";
        }
        else  if(Objects.equals(size, "XL")){
            size="Extra Large";
        }*/holder.size.text = size
        Glide.with(context).load(cartModelList[position].image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return cartModelList.size
    }
}
