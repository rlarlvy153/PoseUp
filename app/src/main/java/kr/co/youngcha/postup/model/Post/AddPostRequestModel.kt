package kr.co.youngcha.postup.model.Post

import kr.co.youngcha.postup.model.PostLocationModel
import com.google.gson.annotations.SerializedName

class AddPostRequestModel (
    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocationModel
)