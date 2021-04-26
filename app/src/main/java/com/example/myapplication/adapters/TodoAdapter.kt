package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DetailActivity
import com.example.myapplication.R
import com.example.myapplication.TodoJson

class TodoAdapter(var todoList: ArrayList<TodoJson>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {


        var tv = LayoutInflater.from(parent.context).inflate(R.layout.todos_items_layout, parent, false);

        return TodoViewHolder(tv);

    }

    override fun getItemCount(): Int {

        return todoList.size;

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var todof = todoList[position]
        holder.tid.text = todoList[position].tId.toString()
        var tComp = todoList[position].tComp

        if (tComp){
            holder.iComp.setImageResource(R.drawable.ic_baseline_true_outline_24)
        }else {
            holder.iComp.setImageResource(R.drawable.ic_baseline_false_outline_24)
        }

        var id = todof.tId
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("tk", "todo")
            holder.itemView.context.startActivity(intent)
        }
    }

    class TodoViewHolder(tv: View) : RecyclerView.ViewHolder(tv) {

        lateinit var tid: TextView
        lateinit var iComp: ImageView


        init {
            tid = tv.findViewById(R.id.tid)
            iComp = tv.findViewById(R.id.t_img)
        }
    }
}