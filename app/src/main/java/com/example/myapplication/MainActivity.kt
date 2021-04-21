package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bumptech.glide.load.model.LazyHeaders

import com.bumptech.glide.load.model.GlideUrl




class MainActivity : AppCompatActivity() {

    var rf = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    var API = rf.create(JsonApi::class.java)

    lateinit var recyclerView : RecyclerView
    lateinit var recyclerViewPic : RecyclerView
    lateinit var recyclerViewTodo : RecyclerView

    lateinit var myAdapter : Adapter
    lateinit var picAdapter : PicAdapter
    lateinit var todoAdapter : TodoAdapter

    lateinit var postBtn : Button
    lateinit var photoBtn :Button
    lateinit var todoBtn : Button

    lateinit var lineBelowPost : View
    lateinit var lineBelowPhoto : View
    lateinit var lineBelowTodo : View

    var isPost = true
    var isPhoto = false
    var isTodo = false

    var list  =  ArrayList<Post>()
    var picList = ArrayList<PhotoJson>()
    var todoList = ArrayList<TodoJson>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView =  findViewById(R.id.rv_list)
        recyclerViewPic = findViewById(R.id.pic_rv_list)
        recyclerViewTodo = findViewById(R.id.tools_rv_list)

        postBtn = findViewById(R.id.posts)
        photoBtn = findViewById(R.id.photos)
        todoBtn = findViewById(R.id.todos)

        lineBelowPost = findViewById(R.id.lineBelowPost)
        lineBelowPhoto = findViewById(R.id.lineBelowPhoto)
        lineBelowTodo = findViewById(R.id.lineBelowTodo)
        myAdapter = Adapter(list)
        picAdapter = PicAdapter(picList)
        todoAdapter = TodoAdapter(todoList)

        supportActionBar?.apply {
            elevation = 0F
        }

        isPost = true
        isPhoto = false
        isTodo = false
        scrViewChange()

        postBtn.setOnClickListener(View.OnClickListener {
            isPost = true
            isPhoto = false
            isTodo = false
            scrViewChange()
        })
        photoBtn.setOnClickListener(View.OnClickListener {
            isPost = false
            isPhoto = true
            isTodo = false
            scrViewChange()
        })
        todoBtn.setOnClickListener(View.OnClickListener {
            isPost = false
            isPhoto = false
            isTodo = true
            scrViewChange()
        })

    }

//    private fun loadPhotos() {
//
//    }

    private fun scrViewChange() {
       if(isPost){
           postBtn.setTextColor(Color.parseColor("#FFFFFF"))
           photoBtn.setTextColor(Color.parseColor("#82afa8"))
           todoBtn.setTextColor(Color.parseColor("#82afa8"))
           recyclerView.visibility = View.VISIBLE
           recyclerViewPic.visibility = View.GONE
           recyclerViewTodo.visibility = View.GONE
           recyclerView.adapter = myAdapter
           lineBelowPost.setBackgroundColor(Color.parseColor("#FFFFFF"))
           lineBelowPhoto.setBackgroundColor(Color.parseColor("#075E55"))
           lineBelowTodo.setBackgroundColor(Color.parseColor("#075E55"))

           val call = API.getPosts()
           call.enqueue(object : Callback<List<Post>> {
               override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                   if (response != null) {
                       if (response.isSuccessful && response.code() == 200) {
                           list.addAll(response.body())
                           myAdapter.notifyDataSetChanged()
                       }

                   }
               }
               override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {

               }
           })

       }else if(isPhoto){
           postBtn.setTextColor(Color.parseColor("#82afa8"))
           photoBtn.setTextColor(Color.parseColor("#FFFFFF"))
           todoBtn.setTextColor(Color.parseColor("#82afa8"))
           recyclerView.visibility = View.GONE
           recyclerViewPic.visibility = View.VISIBLE
           recyclerViewTodo.visibility = View.GONE
           recyclerViewPic.adapter = picAdapter
           lineBelowPost.setBackgroundColor(Color.parseColor("#075E55"))
           lineBelowPhoto.setBackgroundColor(Color.parseColor("#FFFFFF"))
           lineBelowTodo.setBackgroundColor(Color.parseColor("#075E55"))

           val picCall = API.getPhotos()
           picCall.enqueue(object : Callback<List<PhotoJson>> {
               override fun onResponse(call: Call<List<PhotoJson>>?, response: Response<List<PhotoJson>>?) {
                   if (response != null) {
                       if (response.isSuccessful && response.code() == 200) {
                           picList.addAll(response.body())
                           picAdapter.notifyDataSetChanged()
                       }

                   }
               }

               override fun onFailure(call: Call<List<PhotoJson>>?, t: Throwable?) {

               }
           })
       }else if(isTodo){
           postBtn.setTextColor(Color.parseColor("#82afa8"))
           photoBtn.setTextColor(Color.parseColor("#82afa8"))
           todoBtn.setTextColor(Color.parseColor("#FFFFFF"))
           recyclerView.visibility = View.GONE
           recyclerViewPic.visibility = View.GONE
           recyclerViewTodo.visibility = View.VISIBLE
           recyclerViewTodo.adapter = todoAdapter
           lineBelowPost.setBackgroundColor(Color.parseColor("#075E55"))
           lineBelowPhoto.setBackgroundColor(Color.parseColor("#075E55"))
           lineBelowTodo.setBackgroundColor(Color.parseColor("#FFFFFF"))

           val todoCall = API.getTodos()
           todoCall.enqueue(object : Callback<List<TodoJson>> {
               override fun onResponse(call: Call<List<TodoJson>>?, response: Response<List<TodoJson>>?) {
                   if (response != null) {
                       if (response.isSuccessful && response.code() == 200) {
                           todoList.addAll(response.body())
                           todoAdapter.notifyDataSetChanged()
                       }

                   }
               }

               override fun onFailure(call: Call<List<TodoJson>>?, t: Throwable?) {

               }
           })
       }
    }

//    private fun loadPosts() {
//
//
//    }
}

class Adapter(var list: ArrayList<Post>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
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
                val pic = false
                intent.putExtra("id", id)
                intent.putExtra("pic", pic)
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

class PicAdapter(var piclist: ArrayList<PhotoJson>) : RecyclerView.Adapter<PicAdapter.PicViewHolder>() {
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

        val url = GlideUrl(picUrl, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build())

            Glide.with(holder.itemView.context).load(url).into(holder.pic)

            var id = photof.pid
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                val pic = true
                // intent.putExtra("data",jsonData)
                intent.putExtra("id", id)
                intent.putExtra("pic" , pic)
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
        }
    }
}

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
            val todoV = true
            intent.putExtra("id", id)
            intent.putExtra("todoV" , todoV)
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