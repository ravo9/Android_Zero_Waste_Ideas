package dreamcatcher.zerowasteideas.data.database.protips

import androidx.lifecycle.LiveData
import androidx.room.Room
import dreamcatcher.zerowasteideas.general.ZeroWasteIdeasApp
import dreamcatcher.zerowasteideas.network.ProtipPojo
import io.reactivex.Observable
import io.reactivex.subjects.SingleSubject
import kotlinx.coroutines.launch

// Interactor used for communication between data repository and internal database
class ProtipsDatabaseInteractor() {

    var protipsDatabase: ProtipsDatabase? = null

    init {
        val context = ZeroWasteIdeasApp.getAppContext()
        if (context != null) {
            protipsDatabase = Room.databaseBuilder(ZeroWasteIdeasApp.getAppContext()!!, ProtipsDatabase::class.java, "protips_database").build()
        }
    }

    fun getAllProtips(): LiveData<List<ProtipEntity>>? {
        return protipsDatabase?.getProtipsDao()?.getAllSavedProtips()
    }

    // This function should be checked again.
    fun addProtipsSet(protipsSet: List<ProtipPojo>): Observable<Result<Boolean>> {

        val dataUpdateFinishedStatus = SingleSubject.create<Result<Boolean>>()

        launch {
            protipsDatabase?.getProtipsDao()?.clearDatabase().also {
                protipsSet.forEach {
                    val protipEntity = ProtipEntity(
                        id = it.id,
                        protipText = it.protipText
                    )
                    launch {
                        protipsDatabase?.getProtipsDao()?.insertNewProtip(protipEntity)
                    }
                }

            }.also {
                dataUpdateFinishedStatus.onSuccess(Result.success(true))
            }
        }

        return dataUpdateFinishedStatus.toObservable()
    }
}