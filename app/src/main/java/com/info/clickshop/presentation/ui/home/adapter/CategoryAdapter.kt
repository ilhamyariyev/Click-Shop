package com.info.clickshop.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.databinding.ItemCategoriesBinding

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    inner class CategoryHolder(private val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String) {
            with(binding){
                category=item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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