package dreamcatcher.zerowasteideas.features.detailedView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.data.repositories.ItemsRepository
import dreamcatcher.zerowasteideas.general.MailJetService

class NewIdeaViewViewModel : ViewModel() {

    private var itemsRepository = ItemsRepository()

    /*fun getSingleSavedItemById(itemId: String): LiveData<ItemEntity>? {
        return itemsRepository.getSingleSavedItemById(itemId)
    }*/

    fun sendNewIdea() {
        MailJetService.main()
    }
}