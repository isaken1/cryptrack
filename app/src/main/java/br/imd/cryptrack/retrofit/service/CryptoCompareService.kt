package br.imd.cryptrack.retrofit.service

import br.imd.cryptrack.model.CryptoCompareCoinListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CryptoCompareService {

    @GET("/data/all/coinlist")
    @Headers("authorization:" +
            " d89cb17278a08d999fe8155a04474fc60507f0d83de96c4f7989122d69ea9d40")
    fun getAllCoins(): Call<CryptoCompareCoinListResponse>

}