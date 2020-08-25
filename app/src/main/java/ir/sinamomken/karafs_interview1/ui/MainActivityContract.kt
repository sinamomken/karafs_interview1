package ir.sinamomken.karafs_interview1.ui

import io.reactivex.Flowable
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity

interface MainActivityContract {
    interface View{
        fun invokePresenterToCallApiAndSaveInDb(authString: String)
        fun subscribeToDataFromDatabase()
    }

    interface Presenter{
        fun callApiAndSaveInDb(authString: String)
        fun getDataFromDatabase() : Flowable<List<NameEntity>>?
        fun onActivityDestroy()
    }
}