package app.web.postup.Network

import app.web.postup.Model.PostModel
import app.web.postup.Model.RequestModel.PostResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponseModel>

    @GET("pupost/{user_id}")
    fun getPostById(@Path("user_id") userId:Int): Single<PostModel>
//
//    companion object {
//        fun getPostList(): Single<PostResponseModel>{
//            return RetrofitCreator.create(getPostListApiImpl::class.java)
//                .getPostList()
//        }
//    }

}
