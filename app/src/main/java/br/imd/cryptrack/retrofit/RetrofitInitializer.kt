package br.imd.cryptrack.retrofit

import br.imd.cryptrack.service.CryptoCompareService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://min-api.cryptocompare.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun cryptoCompareService() : CryptoCompareService {
        return retrofit.create(CryptoCompareService::class.java)
    }
}