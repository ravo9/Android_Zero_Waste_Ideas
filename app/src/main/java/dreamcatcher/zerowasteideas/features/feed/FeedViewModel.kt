package dreamcatcher.zerowasteideas.features.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.data.database.protips.ProtipEntity
import dreamcatcher.zerowasteideas.data.repositories.ItemsRepository

class FeedViewModel : ViewModel() {

    private val itemsRepository = ItemsRepository()
    private val protipsRepository = ProtipsRepository()

    fun updateItemsDatabaseWithServer(): LiveData<Boolean>? {
        return itemsRepository.updateDatabaseWithServer()
    }

    fun updateProtipsDatabaseWithServer(): LiveData<Boolean>? {
        return protipsRepository.updateDatabaseWithServer()
    }

    fun getAllItems(): LiveData<List<ItemEntity>>? {
        return itemsRepository.getAllItems()
    }

    fun getAllProtips(): LiveData<List<ProtipEntity>>? {
        return protipsRepository.getAllProtips()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsRepository.getNetworkError()
    }
}