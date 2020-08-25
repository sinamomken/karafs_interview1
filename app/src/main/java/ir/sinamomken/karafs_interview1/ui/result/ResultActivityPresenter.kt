package ir.sinamomken.karafs_interview1.ui.result

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ir.sinamomken.karafs_interview1.KarafsApplication
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity

class ResultActivityPresenter : ResultAtivityContract.Presenter {
    val TAG = ResultActivityPresenter::class.java.simpleName
    val namesDisposable = CompositeDisposable()

    override fun getResult(): Observable<String> {

//        return KarafsApplication.database!!.getNamesDao().getAllNames().flatMap {
//            return@flatMap getResultFromData(it)
//        }

        return Observable.just("example")
    }

    private fun getResultFromData(listOfNameEntities: List<NameEntity>): Observable<String> {
        val namesDao = KarafsApplication.database!!.getNamesDao()
        var resultStr = ""
        for (entity in listOfNameEntities) {
            var listOfRelateds = ArrayList<NameEntity>()
            listOfRelateds.addAll(
                namesDao.getNamesWithSimilarLastName(entity.middleName)
            )
            listOfRelateds.addAll(
                namesDao.getNamesWithSimilarLastName(entity.lastName)
            )

            if (listOfRelateds.size == 0) {
                listOfRelateds.add(NameEntity("no one", "", ""))
            }

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

            resultStr += relatedsStr
        }

        return Observable.just(resultStr)
    }
}
