package br.imd.cryptrack.repository

const val DATABASE_NAME = "dbcryptrack"
const val DATABASE_VERSION = 1
const val TABLE_NAME = "moeda"
const val COLUMN_ID = "_id"
const val COLUMN_NAME = "nomeMoeda"
const val COLUMN_IMG_URL = "caminhoIMG"
const val COLUMN_URL = "urlMoeda"
const val COLUMN_SYMBOL = "simboloMoeda"
const val COLUMN_VOLUME = "volume"
const val COLUMN_OPEN = "open"
const val COLUMN_CLOSE = "close"
const val COLUMN_HIGH = "high"
const val COLUMN_LOW = "low"

/**
 * SQL de criação da tabela moeda na versão 1 do banco
 */
const val SQL_CREATE_TABLE_COIN = "CREATE TABLE $TABLE_NAME " +
        "($COLUMN_ID INTEGER PRIMARY KEY, " +
        "$COLUMN_NAME TEXT NOT NULL, " +
        "$COLUMN_IMG_URL TEXT NULL, " +
        "$COLUMN_URL TEXT NULL, " +
        "$COLUMN_SYMBOL TEXT NOT NULL, " +
        "$COLUMN_VOLUME REAL NULL, " +
        "$COLUMN_OPEN REAL NULL, " +
        "$COLUMN_CLOSE REAL NULL, " +
        "$COLUMN_HIGH REAL NULL, " +
        "$COLUMN_LOW REAL NULL);"