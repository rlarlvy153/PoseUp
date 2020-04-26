package kr.co.youngcha.postup.Model

import com.google.gson.annotations.SerializedName

data class PostLocationModel(
    @SerializedName("lat")
    val lat:Float = .0f,

    @SerializedName("lng")
    val lng:Float = .0f
){
    override fun toString():String = "lat : $lat, lng : $lng"
}