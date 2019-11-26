package app.web.postup

import app.web.postup.PostData.PostModel
import com.google.gson.annotations.SerializedName

class PostResponseModel{
    @SerializedName("puposts")
    val posts:List<PostModel> = listOf()

//    @SerializedName("items")
//    val items:List<PostModel> = listOf()


}
