package kr.co.youngcha.postup.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kr.co.youngcha.postup.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.co.youngcha.postup.model.post.AddPostRequestModel
import kr.co.youngcha.postup.model.PostLocation
import kr.co.youngcha.postup.model.Post
import timber.log.Timber

class MainViewModel : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var postList = MutableLiveData<ArrayList<Post>>()
    var myPostList = MutableLiveData<ArrayList<Post>>()
    var restClient = RestClient.restClient

    init {
        postList.value = ArrayList<Post>()
        myPostList.value = ArrayList<Post>()
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
        var post = AddPostRequestModel(1, text, PostLocation(lat, lng))
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
                        val eachPost = Post(1,post.userId, post.userName,post.text,post.location)
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