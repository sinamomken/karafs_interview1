package ir.sinamomken.karafs_interview1.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface NamesDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertNames(list : List<NameEntity>)

//    @Query( "SELECT * FROM NameEntity WHERE firstName LIKE :first_name")
//    fun getName(first_name:String) : List<NameModel>

    @Query("SELECT * FROM NameEntity")
    fun getAllNames() : Flowable<List<NameEntity>>

    @Query("DELETE FROM NameEntity")
    fun deleteAllNames()
}