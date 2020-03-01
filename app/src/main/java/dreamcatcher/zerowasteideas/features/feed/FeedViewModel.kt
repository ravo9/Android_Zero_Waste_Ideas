package dreamcatcher.zerowasteideas.features.feed

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.data.repositories.ItemsRepository


class FeedViewModel : ViewModel() {

    private val itemsRepository = ItemsRepository()

    fun updateItemsDatabaseWithServer(): LiveData<Boolean>? {
        return itemsRepository.updateDatabaseWithServer()
    }

    fun getAllItems(): LiveData<List<ItemEntity>>? {
        return itemsRepository.getAllItems()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return itemsRepository.getNetworkError()
    }
}