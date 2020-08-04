package kr.co.youngcha.postup.network.model

import com.google.gson.annotations.SerializedName

data class PostLocation(
    @SerializedName("lat")
    val lat:Float = .0f,

    @SerializedName("lng")
    val lng:Float = .0f
){
    override fun toString():String = "lat : $lat, lng : $lng"
}