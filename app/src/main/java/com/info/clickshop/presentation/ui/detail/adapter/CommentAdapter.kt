package com.info.clickshop.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.info.clickshop.data.dto.Comment
import com.info.clickshop.databinding.ItemCommentsBinding

class CommentAdapter :RecyclerView.Adapter<CommentAdapter.CommentHolder>(){

    inner class CommentHolder(private val binding:ItemCommentsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Comment){
            with(binding){
                comment=item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.CommentHolder {
        val view = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, DiffUtilCallback)
}