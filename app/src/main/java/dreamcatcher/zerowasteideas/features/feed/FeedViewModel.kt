package dreamcatcher.zerowasteideas.features.feed

import androidx.lifecycle.ViewModel
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.data.repositories.ItemsRepository
import io.reactivex.Observable


class FeedViewModel : ViewModel() {

    private val itemsRepository = ItemsRepository()

    fun getAllItems(): Observable<Result<List<ItemEntity>>> {
        return itemsRepository.getAllItems()
    }
}