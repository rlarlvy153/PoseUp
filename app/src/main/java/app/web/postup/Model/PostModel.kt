package app.web.postup.Model

import com.google.gson.annotations.SerializedName

data class PostLocationModel(
    @SerializedName("lat")
    val lat:Double = .0,

    @SerializedName("lng")
    val lng:Double = .0
    ){
    override fun toString():String = "lat : $lat, lng : $lng"
}

data class PostModel(
    @SerializedName("user_id")
    val userId : Int,

    @SerializedName("user_name")
    val userName : String = "",

    @SerializedName("text")
    val text:String="",

    @SerializedName("location")
    var location : PostLocationModel
    ){
    override fun toString():String ="$userName said \"$text\" at $location"

}