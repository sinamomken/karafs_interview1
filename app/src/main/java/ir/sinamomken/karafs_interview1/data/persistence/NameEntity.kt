package ir.sinamomken.karafs_interview1.data.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NameEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = 0,
    val firstName:String,
    val middleName:String,
    val lastName:String
)