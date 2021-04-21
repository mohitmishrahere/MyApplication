package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonApi {

    //POSTS
    @GET("posts")
    fun getPosts() : retrofit2.Call<List<Post>>

    @GET("posts/{id}")
    fun getValFromId(
            @Path("id") pId: Int
    ) : retrofit2.Call<Post>

    //PHOTOS
    @GET("photos")
    fun getPhotos() : retrofit2.Call<List<PhotoJson>>

    @GET("photos/{id}")
    fun getPhotosFromId(
            @Path("id") pId: Int
    ) : retrofit2.Call<PhotoJson>

    //TODOS
    @GET("todos")
    fun getTodos() : retrofit2.Call<List<TodoJson>>

    @GET("todos/{id}")
    fun getTodosFromId(
            @Path("id") pId: Int
    ) : retrofit2.Call<TodoJson>
}