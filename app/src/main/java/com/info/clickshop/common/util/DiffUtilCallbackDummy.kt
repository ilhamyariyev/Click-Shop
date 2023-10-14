package com.info.clickshop.common.util

import androidx.recyclerview.widget.DiffUtil
import com.info.clickshop.data.dto.Product


object DiffUtilCallbackDummy : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean {
        return oldItem==newItem
    }
}