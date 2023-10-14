package com.info.clickshop.presentation.ui.detail.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.R
import com.info.clickshop.databinding.ItemSizesBinding

class SizesAdapter : RecyclerView.Adapter<SizesAdapter.SizesViewHolder>() {

    private val sizesList = mutableListOf<SizeItem>()

    inner class SizesViewHolder(private val binding: ItemSizesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SizeItem) {
            with(binding){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_launcher_background)
                val wrappedDrawable = drawable?.let { DrawableCompat.wrap(it) }
                if (wrappedDrawable != null) {
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#40BFFF"))
                }
                if (wrappedDrawable != null) {
                    DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_ATOP)
                }
               // drawable?.setColorFilter(Color.parseColor("#40BFFF"), PorterDuff.Mode.SRC_ATOP)
                imageShadow.setImageDrawable(drawable)
                imageShadow.visibility = if (item.isSelected) View.VISIBLE else View.GONE
                itemView.setOnClickListener {
                    item.isSelected = !item.isSelected
                    notifyItemChanged(bindingAdapterPosition)
                }
                textViewNumber.text = item.size

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewHolder {
        val view = ItemSizesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SizesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SizesViewHolder, position: Int) {
        val sizeItem = sizesList[position]
        holder.bind(sizeItem)
    }

    override fun getItemCount(): Int {
        return sizesList.size
    }

    fun setSizes(sizes: List<SizeItem>) {
        sizesList.clear()
        sizesList.addAll(sizes)
        notifyDataSetChanged()
    }
}