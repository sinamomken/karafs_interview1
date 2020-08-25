package ir.sinamomken.karafs_interview1.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.sinamomken.karafs_interview1.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() , ResultAtivityContract.View{
    val TAG = ResultActivity::class.java.name
    val resultActivityPresenter = ResultActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        subscribeToResultFromPresenter()
    }

//    override fun subscribeToDataFromDatabase() {
//        Log.i(TAG, "subscribeToDataFromDatabase: ")
//        resultActivityPresenter.getDataFromDatabase()?.let {
//            it.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { nameEntitiesList ->
//                        Log.i(TAG, "from Presenter -->\n" + nameEntitiesList.toString())
//                        result_tv.text = nameEntitiesList.toString()
//                    },
//                    {error -> Log.e(TAG, error.message!!)}
//                )
//        }
//    }

    override fun subscribeToResultFromPresenter() {
        TODO("Not yet implemented")
    }
}