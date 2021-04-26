package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.myapplication.*

class PicAdapter(var piclist: ArrayList<PhotoJson>, var callFrom : String) : RecyclerView.Adapter<PicAdapter.PicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {

        var pv = LayoutInflater.from(parent.context).inflate(R.layout.pic_item_layout, parent, false);

        return PicViewHolder(pv);

    }

    override fun getItemCount(): Int {


        return piclist.size;

    }

    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
            var photof = piclist[position]
            holder.pid.text = piclist[position].pid.toString()
            holder.pname.text = piclist[position].ptitle
            val picUrl = piclist[position].thumbnailUrl.toString()
            val picFull = piclist[position].url.toString()

        val url = GlideUrl(picUrl, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build())

        val urlFull = GlideUrl(picFull, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build())

            Glide.with(holder.itemView.context).load(url).into(holder.pic)

            var id = photof.pid

            holder.pic.setOnClickListener(View.OnClickListener {
                if(callFrom == "Activity"){
                    Glide.with(holder.itemView.context).load(urlFull).into(imgFull)
                    imgFull.visibility = View.VISIBLE
                    imgBg.visibility = View.VISIBLE
                }else if(callFrom == "Fragment"){
                    Glide.with(holder.itemView.context).load(urlFull).into(imgFront)
                    imgFront.visibility = View.VISIBLE
                    imgBack.visibility = View.VISIBLE
                }
            })
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("tk", "photo")
                holder.itemView.context.startActivity(intent)
        }
    }

    class PicViewHolder(pv: View) : RecyclerView.ViewHolder(pv) {

        lateinit var pid: TextView
        lateinit var pname: TextView
        lateinit var pic: ImageView

        init {
            pid = pv.findViewById(R.id.pid)
            pname = pv.findViewById(R.id.pname)
            pic = pv.findViewById(R.id.pic)
//            imgFull = pv.findViewById(R.id.imgFull)
        }
    }
}