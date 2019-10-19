package br.net.easify.quotes.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Database.Entities.Token
import br.net.easify.quotes.Database.Entities.UsuarioLogado
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.Model.QuoteReacao
import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Services.LoginService
import br.net.easify.quotes.Services.QuoteService
import br.net.easify.quotes.Utils.JWTUtils
import br.net.easify.quotes.Utils.SharedPreferencesHelper
import br.net.easify.quotes.Utils.TokenUtils
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

class QuoteListViewModel(application: Application) : AndroidViewModel(application) {

    private var db = AppDatabase.getAppDataBase(application)!!

    private val quoteService = QuoteService(application)

    val quotes by lazy { MutableLiveData<List<Quote>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    val reacao by lazy { MutableLiveData<Boolean>() }

    private val prefs = SharedPreferencesHelper(getApplication())

    private val disposable = CompositeDisposable()

    fun logout() {
        db.usuarioDao().deleteUsuario()
        db.tokenDao().deleteToken()
        prefs.deleteUserId()
    }

    fun getUserId(): Int {
        return prefs.getUserId()!!.toInt()
    }

    fun refresh() {
        loading.value = true
        getQuotes()
    }

    fun reacao(usuarioId: Int, quoteId: Int, reacaoId: Int) {
        this.gravaReacao(usuarioId, quoteId, reacaoId)
    }

    private fun getQuotes() {

        disposable.add(
            quoteService.getQuotes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Quote>>() {
                    override fun onSuccess(list: List<Quote>) {
                        loading.value = false
                        quotes.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        loadError.value = true
                        loading.value = false
                        quotes.value = arrayListOf()
                    }
                })
        )
    }

    private fun gravaReacao(usuarioId: Int, quoteId: Int, reacaoId: Int) {

        reacao.value = false
        val quoteReacao = QuoteReacao(quoteId, usuarioId, reacaoId, "", "", "")

        disposable.add(
            quoteService.quoteReacao(quoteReacao)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<QuoteReacao>() {
                    override fun onSuccess(res: QuoteReacao) {
                        reacao.value = true
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        reacao.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
