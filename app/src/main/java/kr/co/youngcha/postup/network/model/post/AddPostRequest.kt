package kr.co.youngcha.postup.network.model.post

import kr.co.youngcha.postup.network.model.PostLocation
import com.google.gson.annotations.SerializedName

class AddPostRequest (
    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocation
)