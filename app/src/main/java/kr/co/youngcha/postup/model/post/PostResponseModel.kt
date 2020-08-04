package kr.co.youngcha.postup.model.post

import kr.co.youngcha.postup.model.Post
import com.google.gson.annotations.SerializedName

data class PostResponseModel(
    @SerializedName("puposts")
    val posts:List<Post> = listOf()

//    @SerializedName("items")
//    val items:List<PostModel> = listOf()


)
