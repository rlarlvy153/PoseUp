package kr.co.youngcha.postup.model.Post

import com.google.gson.annotations.SerializedName

data class GetPostByDeltaFromPositionRequestModel(
    @SerializedName("lat")
    val latitute : Double,

    @SerializedName("lng")
    val longitude:Double,

    @SerializedName("delta")
    val delta : Double
)
