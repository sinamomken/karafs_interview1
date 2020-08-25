package ir.sinamomken.karafs_interview1.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.sinamomken.karafs_interview1.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() , ResultAtivityContract.View{
    val TAG = ResultActivity::class.java.name
    val resultActivityPresenter = ResultActivityPresenter()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        subscribeToResultFromPresenter()
    }

    override fun subscribeToResultFromPresenter() {
        Log.i(TAG, "subscribeToResultFromPresenter")
        compositeDisposable.add( resultActivityPresenter.getResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    Log.i(TAG, "result str = " + it)
                    result_tv.text = it
                },
                {error -> Log.e(TAG, error.message!!)}
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


}
