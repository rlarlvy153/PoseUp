package kr.co.youngcha.postup.network.model.post

import kr.co.youngcha.postup.network.model.Post
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("puposts")
    val posts:List<Post> = listOf()

//    @SerializedName("items")
//    val items:List<PostModel> = listOf()


)
