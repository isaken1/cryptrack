package br.imd.cryptrack.model

data class Coin (
    var id: Long,
    var url: String,
    var imageUrl: String?,
    var name: String,
    var imagePath: String?,
    var openPrice: Double?,
    var symbol: String,
    var closePrice: Double?,
    var lowPrice: Double?,
    var highPrice: Double?,
    var volume: Double?
)