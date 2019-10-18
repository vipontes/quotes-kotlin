package br.net.easify.quotes.Services

import android.content.Context
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Interfaces.IQuote
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.Utils.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QuoteService(context: Context) {

    var db = AppDatabase.getAppDataBase(context)!!

    private val api = Retrofit.Builder()
        .baseUrl(Constants.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClientAuth())
        .build()
        .create(IQuote::class.java)

    private fun createClientAuth(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val tokens = db.tokenDao().getToken()
        okHttpClientBuilder.addInterceptor(tokens?.let { AuthenticationInterceptorRefreshToken(it) })
        return okHttpClientBuilder.build()
    }

    fun getQuotes(): Single<List<Quote>> {
        return api.getQuotes()
    }

    fun insertQuote(quote: Quote): Single<Int> {
        return api.insertQuote(quote)
    }

}
