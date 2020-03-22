package app.web.postup.Network

import app.web.postup.Model.PostModel
import app.web.postup.Model.RequestModel.GetPostByDeltaFromPositionResponseModel
import app.web.postup.Model.RequestModel.PostResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponseModel>

    @GET("pupost/{post_id}")
    fun getPostByPostId(@Path("post_id") userId:Int): Single<PostModel>

    @POST("pupost/")
    fun addPost(@Body param : PostModel) : Single<PostModel>

    @GET("pupost/")
    fun getPostByDeltaFromPosition(@Query("lat") lat : Double,
                                    @Query("lng") lng:Double,
                                    @Query("delta") delta:Double) : Single<GetPostByDeltaFromPositionResponseModel>

//    @GET("/admin/puuser")
//    fun getUserInfo(@Query(
//
//    companion object {
//        fun getPostList(): Single<PostResponseModel>{
//            return RetrofitCreator.create(getPostListApiImpl::class.java)
//                .getPostList()
//        }
//    }

}
