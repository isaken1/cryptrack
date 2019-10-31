package br.imd.cryptrack.retrofit

import br.imd.cryptrack.json.ResponseDeserializer
import br.imd.cryptrack.model.CryptoCompareCoinListResponse
import br.imd.cryptrack.service.CryptoCompareService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    val gson: Gson = GsonBuilder().registerTypeAdapter(CryptoCompareCoinListResponse::class.java,
        ResponseDeserializer()).create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://min-api.cryptocompare.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun cryptoCompareService() : CryptoCompareService {
        return retrofit.create(CryptoCompareService::class.java)
    }
}