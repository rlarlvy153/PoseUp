package app.web.postup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.web.postup.Model.PostModel
import app.web.postup.Network.PostApiInterface
import app.web.postup.Network.RetrofitCreator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewModel : ViewModel(){

    var compositeDisposable = CompositeDisposable()
    var postList = MutableLiveData<ArrayList<PostModel>>()
    var restClient = RetrofitCreator.getRetrofitService(PostApiInterface::class.java)

    init{
        postList.value = ArrayList<PostModel>()
    }

    fun getPostList(){
        compositeDisposable.add(
            restClient.getPostList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response->
                    for (item in response.posts) {
                        postList.value?.add(item)
                    }
                    postList.value = postList.value

                }, { error: Throwable ->

                    Timber.d("kgp error  ${error.localizedMessage}")
                }))
    }


}