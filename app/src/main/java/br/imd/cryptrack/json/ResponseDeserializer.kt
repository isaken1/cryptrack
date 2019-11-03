package br.imd.cryptrack.json

import br.imd.cryptrack.model.Coin
import br.imd.cryptrack.model.CryptoCompareCoinListResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Classe criada para desserializar os objetos das respontas da API. Por se tratarem de objetos
 * JSON complexos (com múltiplos objetos aninhados), é necessário fazer um tratamento especial ao
 * se gerar os objetos que representam as moedas.
 */
class ResponseDeserializer: JsonDeserializer<CryptoCompareCoinListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CryptoCompareCoinListResponse {
        val responseObj = json!!.asJsonObject

        val response = responseObj.get("Response")!!.asString
        val message = responseObj.get("Message")!!.asString
        val type = responseObj.get("Type")!!.asInt

        val coinList = mutableListOf<Coin>()

        if (response == "Success") {
            val data = responseObj.get("Data")!!.asJsonObject

            for (key in data.keySet()) {
                val coinJson = data.get(key).asJsonObject

                val coinId = coinJson.get("Id").asLong
                val coinUrl = coinJson.get("Url").asString
                val imageUrl = coinJson.get("ImageUrl")?.asString
                val name = coinJson.get("CoinName").asString
                val symbol = coinJson.get("Symbol").asString

                coinList.add(Coin(coinId, coinUrl, imageUrl, name, symbol))
            }
        }

        return CryptoCompareCoinListResponse(response = response,
            message = message,
            type = type,
            data = coinList)
    }
}