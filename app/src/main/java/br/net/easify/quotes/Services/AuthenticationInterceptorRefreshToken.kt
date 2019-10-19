package br.net.easify.quotes.Services

import android.util.Log
import br.net.easify.quotes.Database.Entities.Token
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthenticationInterceptorRefreshToken @Inject constructor(private val tokens: Token) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {

        synchronized(this) {
            val originalRequest = chain.request()

            val authenticationRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${tokens.token}")
                .build()

            val initialResponse = chain.proceed(authenticationRequest)

            when {
                initialResponse.code() == 403 || initialResponse.code() == 401 -> {



// https://medium.com/@emmanuelguther/android-refresh-token-with-multiple-calls-with-retrofit-babe5d1023a1

                    return initialResponse
                }
                else -> return initialResponse
            }
        }
    }

}