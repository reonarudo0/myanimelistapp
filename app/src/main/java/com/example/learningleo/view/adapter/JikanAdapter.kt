package com.example.learningleo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningleo.R
import com.example.learningleo.domain.Anime
import com.example.learningleo.domain.AnimeGroup
import kotlinx.android.synthetic.main.jikan_vertical_rv.view.*

class JikanAdapter(val context: Context,private val dataSet: Array<AnimeGroup>,val callback: (anime: Anime)-> Unit) : RecyclerView.Adapter<JikanAdapter.JikanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JikanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jikan_vertical_rv,parent,false)
        return JikanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: JikanViewHolder, position: Int) {
        val group = dataSet[position]

        holder.title.text = group.title

        val adapter = group.list?.let { JikanHorAdapter(it,callback) }
        holder.recyclerView.layoutManager =  LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.recyclerView.adapter = adapter
        holder.recyclerView.isNestedScrollingEnabled = false
    }

    class JikanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.tvTitle
        val recyclerView : RecyclerView = itemView.rvHorizontal
    }
}


