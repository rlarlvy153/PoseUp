package kr.co.youngcha.postup.network

import kr.co.youngcha.postup.model.Post.AddPostRequestModel
import kr.co.youngcha.postup.model.Post.GetPostByDeltaFromPositionResponseModel
import kr.co.youngcha.postup.model.PostModel
import kr.co.youngcha.postup.model.Post.PostResponseModel
import kr.co.youngcha.postup.model.User.UserInfoResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponseModel>

    @GET("pupost/{post_id}")
    fun getPostByPostId(@Path("post_id") postId:Long): Single<PostModel>

    @POST("pupost/")
    fun addPost(@Body param : AddPostRequestModel) : Single<PostModel>

    @GET("pupost/")
    fun getPostByDeltaFromPosition(@Query("lat") lat : Double,
                                    @Query("lng") lng:Double,
                                    @Query("delta") delta:Double) : Single<GetPostByDeltaFromPositionResponseModel>

    @GET("puuser/{user_id}")
    fun getUserInfo(@Path("user_id") userId : Long) : Single<UserInfoResponseModel>
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
