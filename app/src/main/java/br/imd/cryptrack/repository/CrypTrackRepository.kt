package br.imd.cryptrack.repository

import br.imd.cryptrack.model.Coin

interface CrypTrackRepository {
    fun save(coin: Coin)
    fun remove(coin: Coin)
    fun list(callback:(MutableList<Coin>) -> Unit)
}