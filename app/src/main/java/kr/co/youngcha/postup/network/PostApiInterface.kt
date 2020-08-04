package kr.co.youngcha.postup.network

import kr.co.youngcha.postup.model.post.AddPostRequest
import kr.co.youngcha.postup.model.post.GetPostByDeltaFromPositionResponse
import kr.co.youngcha.postup.model.Post
import kr.co.youngcha.postup.model.post.PostResponse
import kr.co.youngcha.postup.model.user.UserInfo
import io.reactivex.Single
import retrofit2.http.*

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponse>

    @GET("pupost/{post_id}")
    fun getPostByPostId(@Path("post_id") postId:Long): Single<Post>

    @POST("pupost/")
    fun addPost(@Body param : AddPostRequest) : Single<Post>

    @GET("pupost/")
    fun getPostByDeltaFromPosition(@Query("lat") lat : Double,
                                    @Query("lng") lng:Double,
                                    @Query("delta") delta:Double) : Single<GetPostByDeltaFromPositionResponse>

    @GET("puuser/{user_id}")
    fun getUserInfo(@Path("user_id") userId : Long) : Single<UserInfo>
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
