package app.web.postup.Model.Post

import app.web.postup.Model.PostLocationModel
import com.google.gson.annotations.SerializedName

class AddPostRequestModel (
    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocationModel
)