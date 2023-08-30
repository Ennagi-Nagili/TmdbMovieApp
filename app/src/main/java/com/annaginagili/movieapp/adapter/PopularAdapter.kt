package com.annaginagili.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.movieapp.api.Constants
import com.annaginagili.movieapp.databinding.PopularLayoutBinding
import com.annaginagili.movieapp.model.MovieModel
import com.bumptech.glide.Glide
import kotlin.math.floor

class PopularAdapter(private val context: Context, private val itemList: List<MovieModel>):
    RecyclerView.Adapter<PopularAdapter.ItemHolder>() {
    private lateinit var listener: OnItemClickListener

    class ItemHolder(private val binding: PopularLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: MovieModel, listener: OnItemClickListener, context: Context) {
            Glide.with(context).load(Constants.imageUrl + item.poster_path).into(binding.image)
            if (item.title.length > 13) {
                binding.title.text = item.title.subSequence(0, 13).toString() + ".."
            } else {
                binding.title.text = item.title
            }
            binding.genre.text = item.genres[0].name
            binding.rate.text = (floor(item.vote_average) / 2).toString()

            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = PopularLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(itemList[position], listener, context)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}