package br.net.easify.quotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.Services.QuoteService
import br.net.easify.quotes.Utils.JWTUtils
import br.net.easify.quotes.Utils.TokenUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class NewQuoteViewModel (application: Application) : AndroidViewModel(application) {

    private val quoteService = QuoteService()
    private val disposable = CompositeDisposable()
    private var db = AppDatabase.getAppDataBase(application)!!

    val quoteId by lazy { MutableLiveData<Int>() }
    val error by lazy { MutableLiveData<Boolean>() }

    fun insertQuote(content: String) {

        val tokens = db.tokenDao().getToken()
        val token = tokens!!.token

        var tokenDecoded: JSONObject?
        token.let {
            val decoder = JWTUtils()
            tokenDecoded = decoder.decodeBody(token)
        }

        if (tokenDecoded != null) {
            var usuarioId = 0
            tokenDecoded?.get("usuarioId")?.let {
                usuarioId = it.toString().toInt()
            }

            if (usuarioId > 0) {

                var quote = Quote(0, usuarioId, "", content, 0, 0, "", "")
                this.insertQuote(quote)
                return
            }
        }
    }

    private fun insertQuote(quote: Quote) {

        val tokens = db.tokenDao().getToken()
        val token = tokens?.token

        disposable.add(
            quoteService.insertQuote(quote, "Bearer $token")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(res: Int) {
                        quoteId.value = res
                        error.value = false
                    }

                    override fun onError(e: Throwable) {
                        val tokenUtils = TokenUtils(getApplication())
                        if ( tokenUtils.validateToken() ) {

                        } else {
                            e.printStackTrace()
                            error.value = true
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