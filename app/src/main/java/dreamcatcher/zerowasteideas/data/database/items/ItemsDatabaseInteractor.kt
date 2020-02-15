package dreamcatcher.zerowasteideas.data.database.items

import androidx.lifecycle.LiveData
import androidx.room.Room
import dreamcatcher.zerowasteideas.general.ZeroWasteIdeasApp
import dreamcatcher.zerowasteideas.network.ItemPojo
import io.reactivex.Observable
import io.reactivex.subjects.SingleSubject
import kotlinx.coroutines.launch

// Interactor used for communication between data repository and internal database
class ItemsDatabaseInteractor() {

    var itemsDatabase: ItemsDatabase? = null

    init {
        val context = ZeroWasteIdeasApp.getAppContext()
        if (context != null) {
            itemsDatabase = Room.databaseBuilder(ZeroWasteIdeasApp.getAppContext()!!, ItemsDatabase::class.java, "items_database").build()
        }
    }

    fun getSingleSavedItemById(id: String): LiveData<ItemEntity>? {
        return itemsDatabase?.getItemsDao()?.getSingleSavedItemById(id)
    }

    fun getAllItems(): LiveData<List<ItemEntity>>? {
        return itemsDatabase?.getItemsDao()?.getAllSavedItems()
    }

    // This function should be checked again.
    fun addItemsSet(itemsSet: List<ItemPojo>): Observable<Result<Boolean>> {

        val dataUpdateFinishedStatus = SingleSubject.create<Result<Boolean>>()

        launch {
            itemsDatabase?.getItemsDao()?.clearDatabase().also {
                itemsSet.forEach {
                    val itemEntity = ItemEntity(
                        id = it.id,
                        name = it.name,
                        tags = it.tags.split(" "),
                        imageLink = it.imageLink,
                        binType = it.binType,
                        additionalInformation = it.additionalInformation)
                    launch {
                        itemsDatabase?.getItemsDao()?.insertNewItem(itemEntity)
                    }
                }

            }.also {
                dataUpdateFinishedStatus.onSuccess(Result.success(true))
            }
        }

        return dataUpdateFinishedStatus.toObservable()
    }
}