package dreamcatcher.zerowasteideas.data.repositories

import androidx.lifecycle.LiveData
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.network.ItemsNetworkInteractor
import io.reactivex.Observable

// Data Repository - the main gate of the model (data) part of the application
class ItemsRepository () {

    private val networkInteractor = ItemsNetworkInteractor()

    fun getSingleSavedItemById(id: String): LiveData<ItemEntity>? {
        return null
        //return databaseInteractor.getSingleSavedItemById(id)
    }

    fun getAllItems(): Observable<Result<List<ItemEntity>>> {
        return networkInteractor.getAllItemsFirebaseDb()
    }
}