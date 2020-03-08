package app.web.postup.Model.RequestModel

import app.web.postup.Model.PostModel
import com.google.gson.annotations.SerializedName

data class PostResponseModel(
    @SerializedName("puposts")
    val posts:List<PostModel> = listOf()

//    @SerializedName("items")
//    val items:List<PostModel> = listOf()


)
