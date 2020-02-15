package dreamcatcher.zerowasteideas.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/uc?authuser=0&id=16-nnDpeAozVo6B5CINlF0peLkw5uoXSC&export=download")
    fun getItems(): Observable<List<ItemPojo>>

    @GET("/uc?authuser=0&id=1UO15vtyfnx4Ld29JRDzXaj7XYpnCUV1A&export=download")
    fun getProtips(): Observable<List<ProtipPojo>>
}