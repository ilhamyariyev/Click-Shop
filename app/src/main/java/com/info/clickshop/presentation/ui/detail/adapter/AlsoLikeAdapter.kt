package com.info.clickshop.presentation.ui.detail.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.common.util.DiffUtilCallbackDummy
import com.info.clickshop.data.dto.Product
import com.info.clickshop.databinding.ItemAlsoLikeProductsBinding
import com.info.clickshop.databinding.ItemProductsBinding
import com.info.clickshop.presentation.ui.detail.DetailFragmentDirections
import com.info.clickshop.presentation.ui.home.HomeFragmentDirections
import com.info.clickshop.presentation.ui.home.adapter.FlashSaleAdapter

class AlsoLikeAdapter : RecyclerView.Adapter<AlsoLikeAdapter.ProductHolder>() {

    inner class ProductHolder(private val binding: ItemAlsoLikeProductsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Product) {
            with(binding){
                product=item
                executePendingBindings()
                textViewOldPrice.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
                cardView.setOnClickListener {
                    Navigation.findNavController(it).navigate(DetailFragmentDirections.todetail(item.id.toString()))
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = ItemAlsoLikeProductsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    val differ = AsyncListDiffer(this, DiffUtilCallbackDummy)

}