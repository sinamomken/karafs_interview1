package ir.sinamomken.karafs_interview1.ui.result

import io.reactivex.Flowable
import io.reactivex.Observable
import ir.sinamomken.karafs_interview1.data.persistence.NameEntity

interface ResultAtivityContract {
    interface View{
        fun subscribeToResultFromPresenter()
    }

    interface Presenter{
        fun getResult() : Flowable<String>
    }
}