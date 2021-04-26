package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.PicAdapter
import com.example.myapplication.adapters.PostAdapter
import com.example.myapplication.adapters.TodoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

lateinit var imgFull : ImageView
lateinit var imgBg : ImageButton

class MainActivity : AppCompatActivity() {

    var rf = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    var API = rf.create(JsonApi::class.java)

    lateinit var recyclerView : RecyclerView
    lateinit var recyclerViewPic : RecyclerView
    lateinit var recyclerViewTodo : RecyclerView

    lateinit var myAdapter : PostAdapter
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

        imgFull = findViewById(R.id.imgFull)
        imgBg = findViewById(R.id.imgBg)

        myAdapter = PostAdapter(list)
        picAdapter = PicAdapter(picList, "Activity")
        todoAdapter = TodoAdapter(todoList)

        imgBg.setOnClickListener(View.OnClickListener {
            imgBg.visibility=View.GONE
            imgFull.visibility = View.GONE
        })

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

    override fun onBackPressed() {
        if(imgFull.visibility == View.VISIBLE){
            imgFull.visibility = View.GONE
            imgBg.visibility = View.GONE
        }else {
            super.onBackPressed()
        }
    }

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
                //   Toast.makeText(applicationContext,"toast message with gravity",Toast.LENGTH_SHORT).show()
               }
           })
       }
    }
}

