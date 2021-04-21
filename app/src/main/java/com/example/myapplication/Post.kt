package com.example.myapplication

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Post{
    @SerializedName("userId")
    var userId = 0
    @SerializedName("id")
    var id = 0
    @SerializedName("title")
    var title:String? = null
    @SerializedName("body")
    var body:String? = null
}