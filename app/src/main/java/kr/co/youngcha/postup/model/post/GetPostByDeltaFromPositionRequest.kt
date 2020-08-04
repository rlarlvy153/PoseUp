package kr.co.youngcha.postup.model.post

import com.google.gson.annotations.SerializedName

data class GetPostByDeltaFromPositionRequest(
    @SerializedName("lat")
    val latitute : Double,

    @SerializedName("lng")
    val longitude:Double,

    @SerializedName("delta")
    val delta : Double
)
