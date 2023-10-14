package com.info.clickshop.presentation.ui.detail.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.R
import com.info.clickshop.databinding.ItemColorsBinding

class ColorsAdapter: RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    private val colorsList = mutableListOf<ColorItem>()

    inner class ColorViewHolder(private val binding: ItemColorsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ColorItem) {
            with(binding){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_launcher_background)
                drawable?.setColorFilter(Color.parseColor(item.color), PorterDuff.Mode.SRC_ATOP)
                imageColor.setImageDrawable(drawable)
                imagePicked.visibility = if (item.isSelected) View.VISIBLE else View.GONE
                itemView.setOnClickListener {
                    item.isSelected = !item.isSelected
                    notifyItemChanged(adapterPosition)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = ItemColorsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val colorItem = colorsList[position]
        holder.bind(colorItem)
    }

    override fun getItemCount(): Int {
        return colorsList.size
    }

    fun setColors(colors: List<ColorItem>) {
        colorsList.clear()
        colorsList.addAll(colors)
        notifyDataSetChanged()
    }
}