package app.web.postup.Model

import com.google.gson.annotations.SerializedName

class PostLocationModel{
    @SerializedName("lat")
    val lat:Double = .0

    @SerializedName("lng")
    val lng:Double = .0

    override fun toString():String = "lat : $lat, lng : $lng"
}

class PostModel{
    @SerializedName("text")
    val text:String=""

    @SerializedName("user_name")
    val userName : String = ""

    @SerializedName("location")
    lateinit var location : PostLocationModel

    override fun toString():String ="$userName said \"$text\" at $location"

}