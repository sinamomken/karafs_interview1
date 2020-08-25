package ir.sinamomken.karafs_interview1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.sinamomken.karafs_interview1.R
import ir.sinamomken.karafs_interview1.data.persistence.NamesDB
import ir.sinamomken.karafs_interview1.data.remote.model.NameModel
import ir.sinamomken.karafs_interview1.data.remote.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),  MainActivityContract.View{
    private val TAG = MainActivity::class.java.name
    val namesDisposable = CompositeDisposable()
    val mainActivityPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    private fun setupListeners(){
        buttonLogin.setOnClickListener {
            val username: String = editTextUsername.text.toString().trim()
            val password: String = editTextPassword.text.toString()

            // Validations
            if (username.isEmpty()) {
                editTextUsername.error = "Username required"
                editTextUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editTextPassword.error = "Password should not be empty"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

//            fetchData(username, password)
            val authStr = RetrofitClient.encodeToBase64(username, password)
            invokePresenterToCallApiAndSaveInDb(authStr)
            subscribeToDataFromDatabase()
        }
    }

//    private fun fetchData(username:String, password:String){
//        val authStr = RetrofitClient.encodeToBase64(username, password)
//
//        //Init API
//        namesDisposable.add(
//                RetrofitClient.sampleApiInstance.getNames(authStr)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { namesList ->
//                            Log.i(TAG, "to show in toast: " + namesList?.get(0)?.firstName)
//                            //Toast.makeText(applicationContext, namesList?.get(0)?.firstName, Toast.LENGTH_LONG).show()
//                            storeData(namesList)
//                        }
//                        ,
//                        {
//                            it.printStackTrace()
//                            Log.e(TAG, "Error in sample api: "+ it.message)
//                        }
//                )
//        )
//    }



//    private fun storeData(namesList : List<NameModel>){
//        NamesDB.get(applicationContext)
//    }

    override fun invokePresenterToCallApiAndSaveInDb(authString: String) {
        mainActivityPresenter.callApiAndSaveInDb(authString)
    }

    override fun subscribeToDataFromDatabase() {
        Log.i(TAG, "subscribeToDataFromDatabase: ")
        mainActivityPresenter.getDataFromDatabase()?.let {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { nameEntitiesList ->
                        Log.i(TAG, "from Presenter -->\n" + nameEntitiesList.toString())
                    },
                    {error -> Log.e(TAG, error.message!!)}
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.onActivityDestroy()
    }
}