package br.imd.cryptrack.model

import com.google.gson.annotations.SerializedName

data class Coin (
    @SerializedName("Id") private val id: Long,
    @SerializedName("Url") private val url: String,
    @SerializedName("ImageUrl") private val imageUrl: String,
    @SerializedName("CoinName") private val name: String,
    @SerializedName("Symbol") private val symbol: String
)