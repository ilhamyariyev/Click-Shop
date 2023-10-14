package com.info.clickshop.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.common.util.DiffUtilCallbackDummy
import com.info.clickshop.data.dto.Product
import com.info.clickshop.databinding.ItemPagingBinding
import com.info.clickshop.presentation.ui.home.HomeFragmentDirections

class PagerAdapter : RecyclerView.Adapter<PagerAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemPagingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            with(binding) {
                product=item
                executePendingBindings()
                pager.setOnClickListener {
                    Navigation.findNavController(it).navigate(HomeFragmentDirections.todetail(item.id.toString()))
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = ItemPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }


    val differ = AsyncListDiffer(this, DiffUtilCallbackDummy)


}