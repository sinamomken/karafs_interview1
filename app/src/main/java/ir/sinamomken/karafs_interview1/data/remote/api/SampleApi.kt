package ir.sinamomken.karafs_interview1.data.remote.api

import io.reactivex.Single
import ir.sinamomken.karafs_interview1.data.remote.model.NameModel
import retrofit2.http.GET
import retrofit2.http.Header

interface SampleApi {
    @GET("android-test")
    fun getNames(
        @Header("Authorization") authorization: String
    ) : Single<List<NameModel>>
}