package kr.co.youngcha.postup.Network

object RestClient {
    val restClient = RetrofitCreator.getRetrofitService(PostApiInterface::class.java)
}