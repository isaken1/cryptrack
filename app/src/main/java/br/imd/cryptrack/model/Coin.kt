package br.imd.cryptrack.model

data class Coin (
    var id: Long,
    var url: String,
    var imageUrl: String?,
    var name: String,
    var symbol: String
)