package app.web.postup.Model.RequestModel

import app.web.postup.Model.PostLocationModel
import com.google.gson.annotations.SerializedName

data class GetPostByDeltaFromPositionModel(
    @SerializedName("user_id")
    val userId : Int,

    @SerializedName("user_name")
    val userName : String = "",

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocationModel
)

data class GetPostByDeltaFromPositionResponseModel(
    @SerializedName("puposts")
    val posts : List<GetPostByDeltaFromPositionModel>
)