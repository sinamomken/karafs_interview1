package ir.sinamomken.karafs_interview1.ui.result

import io.reactivex.Flowable
import io.reactivex.Observable
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity

interface ResultActivityContract {
    interface View{
        fun subscribeToResultFromPresenter()
        fun subscribeToOriginalFromPresenter()
    }

    interface Presenter{
        fun getOriginalDataFromDatabase(): Flowable<String>
        fun getResult() : Flowable<String>
    }
}