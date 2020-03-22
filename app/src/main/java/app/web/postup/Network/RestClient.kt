package app.web.postup.Network

object RestClient {
    val restClient = RetrofitCreator.getRetrofitService(PostApiInterface::class.java)
}