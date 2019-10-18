package br.net.easify.quotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Services.UsuarioService
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class NewAccountViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioService = UsuarioService(application)
    private val disposable = CompositeDisposable()

    val usuarioId by lazy { MutableLiveData<Int>() }
    val errorMessage by lazy { MutableLiveData<String>() }

    fun createNewAccount(nome: String, email: String, senha: String) {
        val usuario = Usuario(0, nome, email, senha, 1, "")
        createNewAccount(usuario)
    }

    private fun createNewAccount(usuario: Usuario) {
        disposable.add(
            usuarioService.insertUser(usuario)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Usuario>() {
                    override fun onSuccess(res: Usuario) {
                        usuarioId.value = res.usuarioId
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