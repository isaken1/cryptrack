package br.imd.cryptrack.model

import com.google.gson.annotations.SerializedName

data class CryptoCompareCoinListResponse (
    val response: String,
    val message: String,
    val data: List<Coin>,
    val type: Int
)