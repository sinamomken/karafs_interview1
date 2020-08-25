package ir.sinamomken.karafs_interview1.data.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity
import ir.sinamomken.karafs_interview1.data.persistence.NamesDB

class NamesRepository {


    class InsertNamesData(val list:List<NameEntity>, val application: Application) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
             NamesDB.get(application).getNamesDao().insertNames(list)
             NamesDB.get(application).getNamesDao().getAllNames().subscribe()
             {
                 it.forEach {
                    Log.i("NamesRepository", "lastNames of Johns: ${it.lastName}")
                 }
             }
            return null
        }
    }
}