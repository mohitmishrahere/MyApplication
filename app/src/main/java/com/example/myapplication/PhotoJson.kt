package com.example.myapplication

import com.google.gson.annotations.SerializedName

class PhotoJson {
    @SerializedName("id")
    var pid = 0
    @SerializedName("title")
    var ptitle:String? = null
    @SerializedName("albumId")
    var albumId = 0
    @SerializedName("url")
    var url:String? = null
    @SerializedName("thumbnailUrl")
    var thumbnailUrl:String? = null
}