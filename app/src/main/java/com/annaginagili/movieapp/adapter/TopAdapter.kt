package com.annaginagili.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.databinding.TopLayoutBinding
import com.annaginagili.movieapp.model.TopItem


class TopAdapter(private val context: Context, arrayList: ArrayList<TopItem>) :
    RecyclerView.Adapter<TopAdapter.MyViewHolder>() {
    private var arrayList = ArrayList<TopItem>()

    init {
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.top_layout, parent, false)
        val binding = TopLayoutBinding.bind(view)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(arrayList[position].image)
        holder.title.text = arrayList[position].title
        holder.date.text = arrayList[position].date
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(itemView: TopLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        var image: ImageView
        var title: TextView
        var date: TextView

        init {
            image = itemView.image
            title = itemView.title
            date = itemView.date
        }
    }
}