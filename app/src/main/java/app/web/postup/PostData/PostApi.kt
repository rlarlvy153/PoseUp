package app.web.postup.PostData

import app.web.postup.PostResponseModel
import app.web.postup.RetrofitCreator
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

class PostApi{
    interface getPostListApiImpl {
        @GET("pupost/")
        fun getPostList(): Single<PostResponseModel>
    }

    companion object {
        fun getPostList(): Single<PostResponseModel>{
            return RetrofitCreator.create(getPostListApiImpl::class.java)
                .getPostList()
        }
    }

}
