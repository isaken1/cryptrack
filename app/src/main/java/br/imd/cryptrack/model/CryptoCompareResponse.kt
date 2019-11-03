package br.imd.cryptrack.model

data class CryptoCompareCoinListResponse (
    val response: String,
    val message: String,
    val data: MutableList<Coin>,
    val type: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CryptoCompareCoinListResponse

        if (response != other.response) return false
        if (data != other.data) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = response.hashCode()
        result = 31 * result + data.hashCode()
        result = 31 * result + type
        return result
    }
}