package kr.co.youngcha.postup.Model.User

import com.google.gson.annotations.SerializedName

class UserInfoResponseModel (
    @SerializedName("id")
    val userId:Long,

    @SerializedName("nickname")
    val nickname:String,

    @SerializedName("pu_post_ids")
    val postIdList : List<Int>
)