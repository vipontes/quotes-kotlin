package br.net.easify.quotes.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Services.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginService = LoginService()
    private val disposable = CompositeDisposable()

    val tokens by lazy { MutableLiveData<Login>() }

    fun login(email: String, senha: String, device: String) {
        checkLogin(email, senha, device)
    }

    private fun checkLogin(email: String, senha: String, device: String) {
        disposable.add(
            loginService.login(email, senha, device)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Login>() {
                    override fun onSuccess(res: Login) {
                        tokens.value = res;
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}