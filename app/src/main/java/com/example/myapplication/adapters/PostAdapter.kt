package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DetailActivity
import com.example.myapplication.Post
import com.example.myapplication.R

class PostAdapter(var list: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        var v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false);

        return ViewHolder(v);

    }

    override fun getItemCount(): Int {



        return list.size;

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var post = list[position]
            holder.id.text = list[position].id.toString()
            holder.name.text = list[position].title

            var id = post.id
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("tk", "post")
                holder.itemView.context.startActivity(intent)
            }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        lateinit var id: TextView
        lateinit var name: TextView

        init {
            id = v.findViewById(R.id.id)
            name = v.findViewById(R.id.name)
        }
    }
}