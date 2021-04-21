package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val userId : TextView = findViewById(R.id.usId)
        val id : TextView = findViewById(R.id.id)
        val name : TextView = findViewById(R.id.name)
        val body : TextView = findViewById(R.id.body)
        val progBar : ProgressBar = findViewById(R.id.progressBar)
        val linLay : LinearLayout = findViewById(R.id.linearLayout)
        val jsonpic : ImageView = findViewById(R.id.jsonPic)
        val lbp : View = findViewById(R.id.lineBelowPic)
        val lbb : View = findViewById(R.id.lineBelowBody)

        val passId = intent.getIntExtra("id", 1)
        val picFun = intent.getBooleanExtra("pic", false)

        var rf = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(JsonApi::class.java)

        if(!picFun){
            val call = API.getValFromId(passId)
            call.enqueue(object : Callback<Post>{
                override fun onResponse(call: Call<Post>?, response: Response<Post>?) {
                    val resp = response?.body()
                    userId.text = resp!!.userId.toString()
                    id.text = resp!!.id.toString()
                    name.text = resp!!.title
                    body.text = resp!!.body
                    jsonpic.visibility = View.GONE
                    lbp.visibility = View.GONE
                    progBar.visibility = View.GONE
                    linLay.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<Post>?, t: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
        }else if(picFun){
            val call = API.getPhotosFromId(passId)
            call.enqueue(object : Callback<PhotoJson>{
                override fun onResponse(call: Call<PhotoJson>?, response: Response<PhotoJson>?) {
                    val resp = response?.body()
                    userId.text = resp!!.albumId.toString()
                    id.text = resp!!.pid.toString()
                    name.text = resp!!.ptitle
                    val picUrl = resp!!.url.toString()

                    val url = GlideUrl(picUrl, LazyHeaders.Builder()
                            .addHeader("User-Agent", "your-user-agent")
                            .build())

                    Glide.with(this@DetailActivity).load(url).into(jsonpic)
                    body.visibility = View.GONE
                    lbb.visibility = View.GONE
                    progBar.visibility = View.GONE
                    linLay.visibility = View.VISIBLE
                    jsonpic.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<PhotoJson>?, t: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
        }

//        val jsonData: Post = intent.getSerializableExtra("data") as Post
//        val userId : TextView = findViewById(R.id.usId)
//        val id : TextView = findViewById(R.id.id)
//        val name : TextView = findViewById(R.id.name)
//        val body : TextView = findViewById(R.id.body)
//
//        userId.setText(jsonData.uuserId)
//        id.setText(jsonData.iid)
//        name.setText(jsonData.ttitle)
//        body.setText(jsonData.bbody)
    }
}