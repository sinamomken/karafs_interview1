package ir.sinamomken.karafs_interview1

import android.util.Log
import androidx.multidex.MultiDexApplication
import ir.sinamomken.karafs_interview1.data.persistence.NamesDB

class KarafsApplication : MultiDexApplication() {
    val TAG = KarafsApplication::class.java.name

    companion object{
        var database: NamesDB ?= null
    }

    override fun onCreate() {
        super.onCreate()
        database = NamesDB.get(applicationContext)
        Log.i(TAG, "onCreate: database filled.")
    }
}