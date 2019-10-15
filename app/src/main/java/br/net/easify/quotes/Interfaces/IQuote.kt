package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.Quote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface IQuote {

    @GET("quotes")
    fun getQuotes(@Header("Authorization") token: String): Single<List<Quote>>
}