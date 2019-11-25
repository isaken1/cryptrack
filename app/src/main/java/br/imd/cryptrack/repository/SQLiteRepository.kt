package br.imd.cryptrack.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.imd.cryptrack.model.Coin

class SQLiteRepository(context: Context): CrypTrackRepository {
    private val helper: CrypTrackSqlHelper = CrypTrackSqlHelper(context)

    private fun insert(coin: Coin){
        val db = helper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_NAME, coin.name)
            put(COLUMN_PATH, coin.imageUrl)
        }

        val id = db.insert(TABLE_NAME, null, cv)
        if(id != -1L){
            coin.id = id
        }
        db.close()
    }

    private fun update(coin: Coin){
        val db = helper.writableDatabase

        val cv = ContentValues().apply{
            put(COLUMN_NAME, coin.name)
            put(COLUMN_PATH, coin.imageUrl)
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
        }else{
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
        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        val path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH))

        return Coin(id, name, path)
    }
}