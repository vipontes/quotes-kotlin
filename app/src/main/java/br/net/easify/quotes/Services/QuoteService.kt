package br.net.easify.quotes.Services

import br.net.easify.quotes.Interfaces.IQuote
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.Utils.Constants
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QuoteService {
    private val api = Retrofit.Builder()
        .baseUrl(Constants.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(IQuote::class.java)

    fun getQuotes(token: String): Single<List<Quote>> {
        return api.getQuotes(token)
    }
}
