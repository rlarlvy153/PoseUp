package kr.co.youngcha.postup.Model.Post

import kr.co.youngcha.postup.Model.PostModel
import com.google.gson.annotations.SerializedName

data class PostResponseModel(
    @SerializedName("puposts")
    val posts:List<PostModel> = listOf()

//    @SerializedName("items")
//    val items:List<PostModel> = listOf()


)
