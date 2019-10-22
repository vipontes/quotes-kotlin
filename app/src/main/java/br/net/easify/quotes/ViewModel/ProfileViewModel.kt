package br.net.easify.quotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Model.ApiResult

import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Services.UsuarioService
import br.net.easify.quotes.Utils.SharedPreferencesHelper

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private var db = AppDatabase.getAppDataBase(application)!!
    private val usuarioService = UsuarioService(application)
    private val prefs = SharedPreferencesHelper(getApplication())

    val name by lazy { MutableLiveData<String>() }
    val email by lazy { MutableLiveData<String>() }

    val saved by lazy { MutableLiveData<Boolean>() }

    fun getCurrentUser() {

        val usuarioId = prefs.getUserId()!!.toInt()
        getUser(usuarioId)
    }

    fun save(usuarioNome: String, usuarioEmail: String) {
        saveUser(usuarioNome, usuarioEmail)
    }

    private fun getUser(usuarioId: Int) {

        disposable.add(
            usuarioService.getUser(usuarioId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Usuario>() {
                    override fun onSuccess(res: Usuario) {
                        name.value = res.usuarioNome
                        email.value = res.usuarioEmail
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun saveUser(usuarioNome: String, usuarioEmail: String) {

        val usuarioId = prefs.getUserId()!!.toInt()

        var usuario = Usuario(usuarioId, usuarioNome, usuarioEmail)
        usuario.usuarioAtivo = 1

        disposable.add(
            usuarioService.updateUser(usuario)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResult>() {
                    override fun onSuccess(res: ApiResult) {
                        saved.value = res.success
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        saved.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}