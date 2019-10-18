package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.Quote
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IQuote {

    @GET("quotes")
    fun getQuotes(): Single<List<Quote>>

    @POST("quote")
    fun insertQuote(@Body data: Quote): Single<Int>

}