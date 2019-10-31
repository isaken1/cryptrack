package br.imd.cryptrack.model

data class Coin (
    private val id: Long,
    private val url: String,
    private val imageUrl: String?,
    private val name: String,
    private val symbol: String
)