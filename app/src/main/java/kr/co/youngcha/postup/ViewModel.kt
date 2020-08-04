package kr.co.youngcha.postup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.youngcha.postup.model.Post.AddPostRequestModel
import kr.co.youngcha.postup.model.PostLocationModel
import kr.co.youngcha.postup.model.PostModel
import kr.co.youngcha.postup.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewModel : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var postList = MutableLiveData<ArrayList<PostModel>>()
    var myPostList = MutableLiveData<ArrayList<PostModel>>()
    var restClient = RestClient.restClient

    init {
        postList.value = ArrayList<PostModel>()
        myPostList.value = ArrayList<PostModel>()
    }

//    fun getPostList() {
//        compositeDisposable.add(
//            restClient.getPostList()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({ response ->
//                    for (item in response.posts) {
//                        postList.value?.add(item)
//                    }
//                    postList.value = postList.value
//
//                }, { error: Throwable ->
//
//                    Timber.d("kgp error  ${error.localizedMessage}")
//                })
//        )
//    }

    fun getPostByPostId(postId: Long) {

        compositeDisposable.add(
            restClient.getPostByPostId(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->

                    myPostList.value?.add(response)
                    myPostList.value = myPostList.value
                    Timber.d("${response.text} added")
                }, {

                })
        )
    }

    fun addPost(text: String, lat: Float, lng: Float) {
        var post = AddPostRequestModel(1, text, PostLocationModel(lat, lng))
        compositeDisposable.add(
            restClient.addPost(post)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    postList.value?.add(response)
                    postList.value = postList.value
                    Timber.d("succ")

                }, {
                    Timber.d("error addPost ${it.localizedMessage}")
                })

        )
    }

    fun getPostByRangeFromHere(lat: Double, lng: Double, delta: Double) {
        compositeDisposable.add(
            restClient.getPostByDeltaFromPosition(lat, lng, delta)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response->
                    for( post in response.posts){
                        val eachPost = PostModel(1,post.userId, post.userName,post.text,post.location)
                        postList.value?.add(eachPost)
                    }
                    postList.value = postList.value

                }, {
                    Timber.d("error getPostByRangeFromHere $it")
                })
        )


    }
    fun getUserInfo(userId:Long){
        compositeDisposable.add(
            restClient.getUserInfo(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response->
                    Timber.d("user name ${response.nickname}")
                    Timber.d("user id ${response.userId}")

                    for(id in response.postIdList) {
                        getPostByPostId(id)
                    }


                }, {
                    Timber.d("error getUserInfo $it")
                })
        )

    }

}