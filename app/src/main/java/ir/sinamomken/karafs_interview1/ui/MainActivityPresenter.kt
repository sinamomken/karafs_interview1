package ir.sinamomken.karafs_interview1.ui

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.sinamomken.karafs_interview1.KarafsApplication
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity
import ir.sinamomken.karafs_interview1.data.remote.RetrofitClient
import ir.sinamomken.karafs_interview1.data.remote.model.NameModel

class MainActivityPresenter() : MainActivityContract.Presenter{
    val TAG = MainActivityPresenter::class.java.simpleName
    val namesDisposable = CompositeDisposable()

    override fun callApiAndSaveInDb(authString: String) {
        namesDisposable.add(
            RetrofitClient.sampleApiInstance.getNames(authString)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { nameModelList ->
                        Log.i(TAG, "from api --> \n" + nameModelList.toString())

                        val nameEntities: MutableList<NameEntity> = ArrayList()
                        for(nameModel:NameModel in nameModelList){
                            val nameEntity = convertToNameEntity(nameModel)
                            nameEntities.add(nameEntity)
                        }

                        Log.i(TAG, "nameEntities = " + nameEntities)

                        KarafsApplication.database?.let{
//                            it.getNamesDao().deleteAllNames()
                            it.getNamesDao().insertNames(nameEntities)
                        }
                    },
                    {
                        error -> Log.e(TAG, error?.message!!)
                    }
                )
        )
    }

    private fun convertToNameEntity(nameModel:NameModel) : NameEntity{
        val firstName = nameModel.firstName
        var middleName:String = ""
        var lastName:String = ""
        var dashIndex: Int = nameModel.lastName.indexOf('-')
        if(dashIndex == -1){
            middleName = ""
            lastName = nameModel.lastName
        }else{
            middleName = nameModel.lastName.substring(0, dashIndex)
            lastName = nameModel.lastName.substring(dashIndex+1, nameModel.lastName.length)
        }
        val nameEntity = NameEntity(firstName = firstName, middleName = middleName, lastName = lastName)
        return nameEntity
    }

    override fun getDataFromDatabase(): Flowable<List<NameEntity>>? {
        var output : Flowable<List<NameEntity>>? = null
        if(KarafsApplication.database == null){
            Log.i(TAG, "KarafsApplication.database = null")
        }
        output = KarafsApplication.database?.let {
            it.getNamesDao().getAllNames()
//                .switchMap { data ->
//                    Log.i(TAG, "from database --> \n" + data.toString())
//                    Flowable.just(data)
//            }
        }
        return output
    }

    override fun onActivityDestroy() {
        namesDisposable.dispose()
    }
}