package kr.co.youngcha.postup.model.post

import kr.co.youngcha.postup.model.PostLocation
import com.google.gson.annotations.SerializedName

data class GetPostByDeltaFromPositionModel(
    @SerializedName("user_id")
    val userId : Long,

    @SerializedName("user_name")
    val userName : String = "",

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocation
)

data class GetPostByDeltaFromPositionResponseModel(
    @SerializedName("puposts")
    val posts : List<GetPostByDeltaFromPositionModel>
)