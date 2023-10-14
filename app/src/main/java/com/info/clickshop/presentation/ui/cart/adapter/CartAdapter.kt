package com.info.clickshop.presentation.ui.cart.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.databinding.ItemCartBinding

class CartAdapter: RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var count = 1
    var onDelete: (CartDTO) -> Unit = {}
    var onClick: (Int) -> Unit = {}
    var onClickPlus: (Int) -> Unit = {}
    var onClickMinus: (Int) -> Unit = {}

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartDTO) {
            with(binding){
                product = item
                quantity = count
                executePendingBindings()

                btnPlus.setOnClickListener {
                    count+=1
                    quantity=count
                    onClickPlus(item.price)
                }

                btnMinus.setOnClickListener {
                    if (count > 1) {
                        count -= 1
                        quantity = count
                        onClickMinus(item.price)
                    }
                }

                btnDelete.setOnClickListener {
                    onDelete(item)
                }

                itemView.setOnClickListener {
                    onClick(item.id)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        Log.e("Cart Item size",differ.currentList.size.toString())
    }

    object CartDiffUtilCallback : DiffUtil.ItemCallback<CartDTO>() {
        override fun areItemsTheSame(oldItem: CartDTO, newItem: CartDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CartDTO,
            newItem: CartDTO
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, CartDiffUtilCallback)
}