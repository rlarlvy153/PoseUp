package app.web.postup.Network

import app.web.postup.Model.RequestModel.PostResponseModel
import io.reactivex.Single
import retrofit2.http.GET

interface PostApiInterface{

    @GET("pupost/")
    fun getPostList(): Single<PostResponseModel>
//
//    companion object {
//        fun getPostList(): Single<PostResponseModel>{
//            return RetrofitCreator.create(getPostListApiImpl::class.java)
//                .getPostList()
//        }
//    }

}
