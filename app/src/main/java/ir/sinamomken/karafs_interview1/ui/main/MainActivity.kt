package ir.sinamomken.karafs_interview1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.sinamomken.karafs_interview1.R
import ir.sinamomken.karafs_interview1.data.remote.RetrofitClient
import ir.sinamomken.karafs_interview1.ui.result.ResultActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),  MainActivityContract.View{
    private val TAG = MainActivity::class.java.name
    val namesDisposable = CompositeDisposable()
    val mainActivityPresenter =
        MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    private fun setupListeners(){
        buttonLogin.setOnClickListener {
            val username: String = editTextUsername.text.toString().trim()
            val password: String = editTextPassword.text.toString()

            if(validateUserAndPass(username, password)==false)
                return@setOnClickListener


            val authStr = RetrofitClient.encodeToBase64(username, password)
            invokePresenterToCallApiAndSaveInDb(authStr)

            startActivity(
                Intent(this, ResultActivity::class.java)
            )
        }
    }

    private fun validateUserAndPass(username:String, password:String) : Boolean{
        /*if (username.isEmpty()) {
            editTextUsername.error = getString(R.string.username_should_not_be_empty)
            editTextUsername.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            editTextPassword.error = getString(R.string.password_should_not_be_empty)
            editTextPassword.requestFocus()
            return false
        }*/
        return true
    }

    override fun invokePresenterToCallApiAndSaveInDb(authString: String) {
        mainActivityPresenter.callApiAndSaveInDb(authString)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.onActivityDestroy()
    }
}