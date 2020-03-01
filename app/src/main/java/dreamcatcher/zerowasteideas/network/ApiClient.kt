package dreamcatcher.zerowasteideas.network

import io.reactivex.Observable
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET(Constants.ITEMS_URL)
    fun getItems(): Observable<List<ItemPojo>>

}