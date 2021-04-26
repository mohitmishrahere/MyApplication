package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.PicAdapter
import com.example.myapplication.adapters.PostAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotosFragment : Fragment() {
    var rf = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    var API = rf.create(JsonApi::class.java)

    lateinit var recyclerViewPic : RecyclerView
    lateinit var picAdapter : PicAdapter
    var picList  =  ArrayList<PhotoJson>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photos, container, false)
        recyclerViewPic = rootView.findViewById(R.id.photoList) as RecyclerView
        picAdapter = PicAdapter(picList, "Fragment")
        recyclerViewPic.adapter = picAdapter
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

        return rootView
    }

}