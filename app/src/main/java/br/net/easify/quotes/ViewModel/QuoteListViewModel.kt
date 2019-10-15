package br.net.easify.quotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.Services.QuoteService
import br.net.easify.quotes.Utils.TokenUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class QuoteListViewModel(application: Application) : AndroidViewModel(application) {

    private val quoteService = QuoteService()

    val quotes by lazy { MutableLiveData<List<Quote>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private var db = AppDatabase.getAppDataBase(application)!!

    fun refresh() {
        loading.value = true
        getQuotes()
    }

    private fun getQuotes() {

        val tokens = db.tokenDao().getToken()
        val token = tokens?.token

        disposable.add(
            quoteService.getQuotes("Bearer $token")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Quote>>() {
                    override fun onSuccess(list: List<Quote>) {
                        loading.value = false
                        quotes.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        val tokenUtils = TokenUtils(getApplication())
                        if ( !tokenUtils.validateToken() ) {



                        } else {
                            e.printStackTrace()
                            loadError.value = true
                            loading.value = false
                            quotes.value = null
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
