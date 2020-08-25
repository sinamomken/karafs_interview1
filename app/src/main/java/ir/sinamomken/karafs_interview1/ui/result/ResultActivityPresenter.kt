package ir.sinamomken.karafs_interview1.ui.result

import android.util.ArraySet
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ir.sinamomken.karafs_interview1.KarafsApplication
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity
import java.util.concurrent.Flow

class ResultActivityPresenter : ResultAtivityContract.Presenter {
    val TAG = ResultActivityPresenter::class.java.simpleName
    val namesDisposable = CompositeDisposable()

    override fun getResult(): Flowable<String> {
        // Get all entities from DB
        return KarafsApplication.database!!.getNamesDao().getAllNames()
            .map { getResultFromData(it) } // Calculate result string for entities
    }

    private fun getResultFromData(listOfNameEntities: List<NameEntity>): String {
        val namesDao = KarafsApplication.database!!.getNamesDao()
        var resultStr = "\n"

        for (entity in listOfNameEntities) {
            var listOfRelateds = ArraySet<NameEntity>()

            // Finding those with similar middleName
            if(!entity.middleName.equals("")) {
                listOfRelateds.addAll(
                    namesDao.getNamesWithSimilarLastName(entity.middleName)
                )
            }
            // Finding those with similar lastName
            if(!entity.lastName.equals("")) {
                listOfRelateds.addAll(
                    namesDao.getNamesWithSimilarLastName(entity.lastName)
                )
            }
            // Remove itself from list of relateds
            listOfRelateds.remove(entity)

            if (listOfRelateds.size == 0) {
                listOfRelateds.add(NameEntity("no one", "", ""))
            }

            // Creating "Foo is related to Bar1, ..." string
            var relatedsStr: String = "${entity.firstName} is related to "
            var i: Int = 0
            for (relatedEntity in listOfRelateds) {
                relatedsStr += relatedEntity.firstName
                when (i) {
                    listOfRelateds.size - 1 -> relatedsStr += "."
                    listOfRelateds.size - 2 -> relatedsStr += " & "
                    else -> relatedsStr += ", "
                }
                i++
            }

            resultStr += relatedsStr + "\n"
        }

        Log.i(TAG, "resultStr = "+resultStr)
        return resultStr
    }
}
