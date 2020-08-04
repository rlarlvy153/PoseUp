package kr.co.youngcha.postup.network

object RestClient {
    val restClient = RetrofitCreator.getRetrofitService(PostApiInterface::class.java)
}