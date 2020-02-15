package dreamcatcher.zerowasteideas.data.database.protips

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dreamcatcher.zerowasteideas.general.Converters

@Database(entities = [(ProtipEntity::class)], version = 1)
@TypeConverters(Converters::class)
abstract class ProtipsDatabase : RoomDatabase() {
    abstract fun getProtipsDao(): ProtipsDao
}