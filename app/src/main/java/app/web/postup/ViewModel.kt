package app.web.postup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.web.postup.PostData.PostApi
import app.web.postup.PostData.PostModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
        Timber.d("kgp getPOsTList")
        compositeDisposable.add(
            PostApi.getPostList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: PostResponseModel ->
                    Timber.d("kgp post list")
                    for (item in response.posts) {
                        postList.value?.add(item)
                        Log.d("kkkkkk",item.text)
                    }
                    postList.value = postList.value

                }, { error: Throwable ->

                    Timber.d("kgp error  ${error.localizedMessage}")
                }))
    }


}