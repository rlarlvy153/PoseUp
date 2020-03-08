package app.web.postup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.web.postup.PostData.PostApi
import app.web.postup.PostData.PostModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewModel : ViewModel(){

    var compositeDisposable = CompositeDisposable()
    var postList = MutableLiveData<ArrayList<PostModel>>()

    init{
        postList.value = ArrayList<PostModel>()
    }

    fun getPostList(){
        compositeDisposable.add(
            PostApi.getPostList()
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