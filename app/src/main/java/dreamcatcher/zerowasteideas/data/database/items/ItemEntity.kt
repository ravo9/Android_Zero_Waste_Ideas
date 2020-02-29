package dreamcatcher.zerowasteideas.data.database.items

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
        @PrimaryKey val id: String,
        val authorName: String,
        val title: String,
        val description: String,
        //val tags: List<String>,
        val imageLink: String?
)