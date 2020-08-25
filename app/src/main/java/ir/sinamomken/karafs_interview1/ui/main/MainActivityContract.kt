package ir.sinamomken.karafs_interview1.ui.main

import io.reactivex.Flowable
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity

interface MainActivityContract {
    interface View{
        fun invokePresenterToCallApiAndSaveInDb(authString: String)
    }

    interface Presenter{
        fun callApiAndSaveInDb(authString: String)
        fun onActivityDestroy()
    }
}