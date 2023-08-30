package com.annaginagili.movieapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.databinding.CategoriesLayoutBinding
import com.annaginagili.movieapp.model.CategoriesItem

class CategoriesAdapter(private val context: Context, private val itemList: List<CategoriesItem>):
    RecyclerView.Adapter<CategoriesAdapter.ItemHolder>() {
    private lateinit var listener: OnItemClickListener

    class ItemHolder(private val binding: CategoriesLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: CategoriesItem, listener: OnItemClickListener) {
            binding.text.setBackgroundResource(item.background)
            binding.text.text = item.text
            binding.text.setTextColor(Color.parseColor(item.color))

            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = CategoriesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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