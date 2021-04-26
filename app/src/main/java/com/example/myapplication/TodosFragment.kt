package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.PicAdapter
import com.example.myapplication.adapters.TodoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodosFragment : Fragment() {

    var rf = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    var API = rf.create(JsonApi::class.java)

    lateinit var recyclerViewTodo : RecyclerView
    lateinit var todoAdapter : TodoAdapter
    var todoList  =  ArrayList<TodoJson>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_todos, container, false)
        recyclerViewTodo = rootView.findViewById(R.id.todoList) as RecyclerView
        todoAdapter = TodoAdapter(todoList)
        recyclerViewTodo.adapter = todoAdapter
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
        return rootView
    }
}