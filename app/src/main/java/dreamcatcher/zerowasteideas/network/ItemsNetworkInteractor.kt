package dreamcatcher.zerowasteideas.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity

// Interactor used for communication between data repository and the external API
class ItemsNetworkInteractor {

    private val apiClient = NetworkAdapter.apiClient()
    val networkError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllItemsFirebaseDb(): Observable<Result<List<ItemEntity>>>  {
        val allItemsSubject = SingleSubject.create<Result<List<ItemEntity>>>()
        val database = FirebaseDatabase.getInstance().reference
        val query = database.orderByKey()
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val itemsList = ArrayList<ItemEntity>()
                for (item in dataSnapshot.children) {

                    try {
                        if (item.key != null
                            && item.child("Title").value != null
                            && item.child("Author name").value != null) {
                            val itemEntity = ItemEntity(
                                id = (item.key!!).toInt(),
                                authorName = item.child("Author name").value as String,
                                title = item.child("Title").value as String,
                                description = item.child("Description").value as String,
                                tags = (item.child("Tags").value as String).split(" ").toList(),
                                imageLink = item.child("Image").value as String,
                                currentGrade = (item.child("Current grade").value as Long).toInt(),
                                amountOfGrades = (item.child("Amount of grades").value as Long).toInt()
                            )
                            itemsList.add(itemEntity)
                        }
                    }
                    catch (e: Exception) {
                        e.message?.let {
                            Log.e("Exception", it);
                        }
                    }
                }
                allItemsSubject.onSuccess(Result.success(itemsList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                networkError.postValue(true)
                Log.e("getItems() error: ", databaseError.message)
            }
        })

        return allItemsSubject.toObservable()
    }

    fun getAllItems(): Observable<Result<List<ItemPojo>>> {
        val allItemsSubject = SingleSubject.create<Result<List<ItemPojo>>>()

        apiClient.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != null) {
                        allItemsSubject.onSuccess(Result.success(it!!))
                        //connectionEstablishedStatus.postValue(true)
                    } else {
                        Log.e("getItems() error: ", "NULL value")
                    }
                },
                {
                    networkError.postValue(true)
                    Log.e("getItems() error: ", it.message)
                })

        return allItemsSubject.toObservable()
    }
}