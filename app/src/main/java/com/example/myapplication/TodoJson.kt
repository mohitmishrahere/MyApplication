package com.example.myapplication

import com.google.gson.annotations.SerializedName

class TodoJson {
    @SerializedName("userId")
    var tUserId = 0
    @SerializedName("id")
    var tId = 0
    @SerializedName("title")
    var tTitle:String? = null
    @SerializedName("completed")
    var tComp = true
}