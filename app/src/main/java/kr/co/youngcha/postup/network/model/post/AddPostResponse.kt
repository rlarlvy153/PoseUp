package kr.co.youngcha.postup.network.model.post

import kr.co.youngcha.postup.network.model.PostLocation
import com.google.gson.annotations.SerializedName

data class AddPostResponse (
    @SerializedName("id")
    val postId : Long,

    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("user_name")
    val userName : String = "",

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocation
){
    override fun toString():String ="$userName said \"$text\" at $location"

}