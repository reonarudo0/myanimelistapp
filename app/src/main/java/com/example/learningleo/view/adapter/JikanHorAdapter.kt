package com.example.learningleo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.learningleo.R
import com.example.learningleo.domain.Anime
import com.example.learningleo.view.activity.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.jikan_horizontal_rv.view.*

class JikanHorAdapter(private val dataSet: Array<Anime>,val callback: (anime: Anime) -> Unit): RecyclerView.Adapter<JikanHorAdapter.JikanViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JikanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jikan_horizontal_rv,parent,false)
        return JikanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: JikanViewHolder, position: Int) {
        val anime = dataSet[position]
        Picasso.get().load(anime.imageUrl).into(holder.poster)

        holder.bind(anime,callback)
    }

    class JikanViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val poster = itemView.imPoster

        fun bind(anime: Anime,callback: (anime: Anime) -> Unit)
        {
            itemView.setOnClickListener{
               callback(anime)
            }
        }
    }

}
