package kr.co.youngcha.postup.network

import kr.co.youngcha.postup.model.post.AddPostRequestModel
import kr.co.youngcha.postup.model.post.GetPostByDeltaFromPositionResponseModel
import kr.co.youngcha.postup.model.Post
import kr.co.youngcha.postup.model.post.PostResponseModel
import kr.co.youngcha.postup.model.user.UserInfoResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponseModel>

    @GET("pupost/{post_id}")
    fun getPostByPostId(@Path("post_id") postId:Long): Single<Post>

    @POST("pupost/")
    fun addPost(@Body param : AddPostRequestModel) : Single<Post>

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
