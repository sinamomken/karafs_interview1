package ir.sinamomken.karafs_interview1.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.sinamomken.karafs_interview1.data.remote.model.NameModel

@Database(version = 1, entities = [NameEntity::class])
abstract class NamesDB : RoomDatabase() {
    companion object{
        fun get(appContext: Context) : NamesDB{
            return Room.databaseBuilder(appContext, NamesDB::class.java, "names_db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getNamesDao() : NamesDao
}