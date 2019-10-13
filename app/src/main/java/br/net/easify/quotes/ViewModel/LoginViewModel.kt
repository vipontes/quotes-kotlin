package br.net.easify.quotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Services.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import com.google.gson.JsonParser

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginService = LoginService()
    private val disposable = CompositeDisposable()

    val tokens by lazy { MutableLiveData<Login>() }
    val errorMessage by lazy { MutableLiveData<String>() }

    fun login(email: String, senha: String, device: String) {
        checkLogin(email, senha, device)
    }

    fun refreshToken(refreshToken: String) {
        checkRefreshToken(refreshToken)
    }

    private fun checkLogin(email: String, senha: String, device: String) {
        disposable.add(
            loginService.login(email, senha, device)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Login>() {
                    override fun onSuccess(res: Login) {
                        tokens.value = res
                        errorMessage.value = ""
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        if (e is HttpException) {
                            val errorJsonString = e.response().errorBody()?.string()
                            val message = JsonParser().parse(errorJsonString)
                                .asJsonObject["message"]
                                .asString

                            errorMessage.value = message
                        } else {
                            errorMessage.value = "Internal error"
                        }
                    }
                })
        )
    }

    private fun checkRefreshToken(refreshToken: String) {
        disposable.add(
            loginService.refreshToken(refreshToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Login>() {
                    override fun onSuccess(res: Login) {
                        tokens.value = res
                        errorMessage.value = ""
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        if (e is HttpException) {
                            val errorJsonString = e.response().errorBody()?.string()
                            val message = JsonParser().parse(errorJsonString)
                                .asJsonObject["message"]
                                .asString

                            errorMessage.value = message
                        } else {
                            errorMessage.value = "Internal error"
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
