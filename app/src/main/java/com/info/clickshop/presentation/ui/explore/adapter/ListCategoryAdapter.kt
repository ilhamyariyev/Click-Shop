package com.info.clickshop.presentation.ui.explore.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.databinding.ItemListCategoryBinding

class ListCategoryAdapter() : RecyclerView.Adapter<ListCategoryAdapter.CategoryHolder>() {

    var selected = false

    inner class CategoryHolder(private val binding: ItemListCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String) {
            with(binding){
                category=item
                executePendingBindings()
                categoryCL.setOnClickListener {
                    if (!selected){
                        categoryCL.setBackgroundColor(Color.parseColor("#EBF0FF"))
                        textView28.setTextColor(Color.parseColor("#40BFFF"))
                    }


                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, DiffUtilCallback)



}