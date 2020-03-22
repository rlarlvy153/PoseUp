package app.web.postup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.web.postup.Model.PostLocationModel
import app.web.postup.Model.PostModel
import app.web.postup.Network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewModel : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var postList = MutableLiveData<ArrayList<PostModel>>()
    var restClient = RestClient.restClient

    init {
        postList.value = ArrayList<PostModel>()
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

    fun getPostByPostId(userId: Int) {
        compositeDisposable.add(
            restClient.getPostByPostId(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    postList.value?.add(response)
                    postList.value = postList.value
                }, {

                })

        )
    }

    fun addPost(text: String, lat: Double, lng: Double) {
        var post: PostModel = PostModel(1, "kgp", text, PostLocationModel(lat, lng))
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
                        val eachPost = PostModel(1,post.userName,post.text,post.location)
                        postList.value?.add(eachPost)
                    }
                    postList.value = postList.value

                }, {
                    Timber.d("error getPostByRangeFromHere $it")
                })
        )


    }
    fun getUserInfo(){
        compositeDisposable.add(
            restClient.getUserInfo(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response->
                    Timber.d("user name ${response.nickname}")
                    Timber.d("user id ${response.userId}")
                    for(id in response.postIdList){
                        Timber.d("id $id")
                    }

                }, {
                    Timber.d("error getUserInfo $it")
                })
        )

    }
}