package dreamcatcher.zerowasteideas.features.detailedView

import androidx.lifecycle.ViewModel
import dreamcatcher.zerowasteideas.data.repositories.ItemsRepository

class NewIdeaViewViewModel : ViewModel() {

    private var itemsRepository = ItemsRepository()

    /*fun getSingleSavedItemById(itemId: String): LiveData<ItemEntity>? {
        return itemsRepository.getSingleSavedItemById(itemId)
    }*/
}