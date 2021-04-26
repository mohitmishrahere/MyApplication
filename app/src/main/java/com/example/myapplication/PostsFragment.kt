package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.PostAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostsFragment : Fragment() {

    var rf = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    var API = rf.create(JsonApi::class.java)

    lateinit var recyclerView : RecyclerView
    lateinit var myAdapter : PostAdapter
    var list  =  ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_posts, container, false)
        recyclerView = rootView.findViewById(R.id.postList) as RecyclerView
        myAdapter = PostAdapter(list)
        recyclerView.adapter = myAdapter
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
        return rootView
    }
}