package br.imd.cryptrack.worker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.imd.cryptrack.repository.SQLiteRepository
import br.imd.cryptrack.retrofit.RetrofitInitializer
import br.imd.cryptrack.util.ACTION_COIN_UPDATED
import br.imd.cryptrack.util.ACTION_FETCH_FINISHED
import timber.log.Timber


class FetchCoinsWorker(context: Context, workerParameters: WorkerParameters):
    Worker(context, workerParameters) {

    private val repository: SQLiteRepository = SQLiteRepository(context)

    override fun doWork(): Result {
        return try {
            val response =
                RetrofitInitializer().cryptoCompareService().getAllCoins().execute().body()

            for (coin in response!!.data) {
                repository.save(coin)
                val intent = Intent(ACTION_COIN_UPDATED)
                val bundle = Bundle(1)
                bundle.putLong("id", coin.id)
                intent.putExtras(bundle)
                applicationContext.sendBroadcast(intent)
            }

            Result.success()
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }
    }
}