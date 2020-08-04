package kr.co.youngcha.postup.model.Post

import kr.co.youngcha.postup.model.PostLocationModel
import com.google.gson.annotations.SerializedName

data class AddPostResponseModel (
    @SerializedName("id")
    val postId : Long,

    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("user_name")
    val userName : String = "",

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocationModel
){
    override fun toString():String ="$userName said \"$text\" at $location"

}