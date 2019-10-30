package br.imd.cryptrack.model

import com.google.gson.annotations.SerializedName

data class CryptoCompareCoinListResponse (
    @SerializedName("Response") val responseType: String,
    @SerializedName("Message") val message: String,
    @SerializedName("Data") val data: List<Coin>?,
    @SerializedName("HasWarning") val hasWarning: Boolean,
    @SerializedName("Type") val type: Int
)