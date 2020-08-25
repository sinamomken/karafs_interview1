package ir.sinamomken.karafs_interview1.data.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["firstName","middleName","lastName"])
data class NameEntity(
//    @PrimaryKey(autoGenerate = true)
//    var id:Int?=null,
    var firstName:String,
    var middleName:String,
    var lastName:String
)