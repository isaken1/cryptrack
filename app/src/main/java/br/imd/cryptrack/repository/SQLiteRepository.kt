package br.imd.cryptrack.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.database.getDoubleOrNull
import br.imd.cryptrack.model.Coin

class SQLiteRepository(context: Context): CrypTrackRepository {
    private val helper: CrypTrackSqlHelper = CrypTrackSqlHelper(context)

    private fun insert(coin: Coin){
        val db = helper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_ID, coin.id)
            put(COLUMN_NAME, coin.name)
            put(COLUMN_SYMBOL, coin.symbol)
            put(COLUMN_URL, coin.url)
            put(COLUMN_IMG_URL, coin.imageUrl)
            put(COLUMN_OPEN, coin.openPrice)
            put(COLUMN_CLOSE, coin.closePrice)
            put(COLUMN_HIGH, coin.highPrice)
            put(COLUMN_LOW, coin.lowPrice)
            put(COLUMN_VOLUME, coin.volume)
        }

        db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    private fun update(coin: Coin){
        val db = helper.writableDatabase

        val cv = ContentValues().apply{
            put(COLUMN_NAME, coin.name)
            put(COLUMN_SYMBOL, coin.symbol)
            put(COLUMN_URL, coin.url)
            put(COLUMN_IMG_URL, coin.imageUrl)
            put(COLUMN_OPEN, coin.openPrice)
            put(COLUMN_CLOSE, coin.closePrice)
            put(COLUMN_HIGH, coin.highPrice)
            put(COLUMN_LOW, coin.lowPrice)
            put(COLUMN_VOLUME, coin.volume)
        }

        db.update(
            TABLE_NAME,
            cv,
            "$COLUMN_ID = ?",
            arrayOf(coin.id.toString())
        )

        db.close()
    }

    override fun save(coin: Coin) {
        if(coin.id == 0L){
            insert(coin)
        } else {
            update(coin)
        }
    }

    override fun remove(coin: Coin) {
        val db = helper.writableDatabase

        db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ? ",
            arrayOf(coin.id.toString())
        )

        db.close()
    }

    override fun list(callback: (MutableList<Coin>) -> Unit) {
        var sql = "SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        sql += " ORDER BY $COLUMN_ID"

        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val coins = ArrayList<Coin>()

        while (cursor.moveToNext()){
            val moeda = coinFromCursor(cursor)
            coins.add(moeda)
        }

        cursor.close()
        db.close()

        callback(coins)
    }

    private fun coinFromCursor(cursor: Cursor): Coin{
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val url = cursor.getString(cursor.getColumnIndex(COLUMN_URL))
        val imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMG_URL))
        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        val symbol = cursor.getString(cursor.getColumnIndex(COLUMN_SYMBOL))
        val openPrice = cursor.getDoubleOrNull(cursor.getColumnIndex(COLUMN_OPEN))
        val closePrice = cursor.getDoubleOrNull(cursor.getColumnIndex(COLUMN_CLOSE))
        val highPrice = cursor.getDoubleOrNull(cursor.getColumnIndex(COLUMN_HIGH))
        val lowPrice = cursor.getDoubleOrNull(cursor.getColumnIndex(COLUMN_LOW))
        val volume = cursor.getDoubleOrNull(cursor.getColumnIndex(COLUMN_VOLUME))


        return Coin(id, url, imageUrl, name, symbol, openPrice, closePrice, highPrice,
            lowPrice, volume)
    }
}