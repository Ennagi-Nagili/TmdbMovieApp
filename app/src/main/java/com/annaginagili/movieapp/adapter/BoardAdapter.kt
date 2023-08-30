package com.annaginagili.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.databinding.BoardLayoutBinding
import com.annaginagili.movieapp.databinding.CategoriesLayoutBinding
import com.annaginagili.movieapp.model.BoardItem
import com.annaginagili.movieapp.model.CategoriesItem

class BoardAdapter(private val context: Context, private val itemList: List<BoardItem>):
    RecyclerView.Adapter<BoardAdapter.ItemHolder>() {
    private lateinit var listener: OnItemClickListener

    class ItemHolder(private val binding: BoardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: BoardItem, listener: OnItemClickListener) {
            binding.background.setImageResource(item.background)
            binding.title.text = item.title
            binding.description.text = item.description
            binding.indicator.setImageResource(item.indicator)
            binding.next.setImageResource(item.next)

            binding.next.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = BoardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(itemList[position], listener)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}