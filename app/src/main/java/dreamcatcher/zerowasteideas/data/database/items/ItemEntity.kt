package dreamcatcher.zerowasteideas.data.database.items

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
        @PrimaryKey val id: Int,
        val authorName: String,
        val title: String,
        val description: String,
        val tags: List<String>?,
        val imageLink: String?,
        val currentGrade: Int,
        val amountOfGrades: Int
)