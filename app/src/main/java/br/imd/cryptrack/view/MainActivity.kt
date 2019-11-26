package br.imd.cryptrack.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.imd.cryptrack.BuildConfig
import br.imd.cryptrack.R
import br.imd.cryptrack.adapter.CoinListAdapter
import br.imd.cryptrack.model.Coin
import br.imd.cryptrack.repository.SQLiteRepository
import br.imd.cryptrack.util.ACTION_COIN_UPDATED
import br.imd.cryptrack.util.ACTION_FETCH_FINISHED
import br.imd.cryptrack.view.fragment.FullListFragment
import br.imd.cryptrack.view.fragment.LoadingFragment
import br.imd.cryptrack.worker.FetchCoinsWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val navigator: BottomNavigationView by lazy { nav }
    private val fullListFragment =  FullListFragment.newInstance()
    private lateinit var adapter: CoinListAdapter
    private val loadingFragment: LoadingFragment = LoadingFragment.newInstance()
    private var coinList: MutableList<Coin> = mutableListOf()
    private val workManager = WorkManager.getInstance(application)
    private val repository = SQLiteRepository(this)

    companion object {
        var currentInstance: MainActivity? = null
        fun getInstance(): MainActivity? {
            return currentInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentInstance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val fetchRequest =
            PeriodicWorkRequestBuilder<FetchCoinsWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        workManager.enqueue(fetchRequest)

        supportActionBar?.title = getString(R.string.app_name)
        // TODO: Adicionar comportamento para navegar para a tela de favoritos por padrão caso haja
        //  pelo menos uma moeda cadastrada como favorita. Caso contrário, mostrar a lista inteira.
        navigator.setOnNavigationItemSelectedListener(this)

        val params = navigator.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = BottomNavigationBehavior()

        navigateTo(loadingFragment)
        fetchDataFromDatabase()
    }

    /**
     * Função necessária para implementar a navegação através do BottomNavigationView.
     * Similar ao onClick dos botões
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_list_all -> {
                fullListFragment.initRecyclerView(adapter)
                navigateTo(fullListFragment as Fragment)
            }
            //TODO: Quando feitos os outros fragments, adicionar a transição deles aqui
            else -> {
                Toast.makeText(this, "Funcionalidade ainda não implementada!",
                    Toast.LENGTH_LONG).show()
            }
        }

        return true
    }

    /**
     * Navega para um outro fragment.
     * @param fragment fragmento que será mostrado ao usuário na tela do aplicativo.
     */
    private fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commitNow()
    }

    /**
     * Puxa todas as moedas disponíveis no banco de dados
     */
    private fun fetchDataFromDatabase() {
        this.repository.list { mutableList: MutableList<Coin> -> setupAdapter(mutableList) }
    }

    /**
     * Inicialista o adapter do RecyclerView.
     * @param coins lista de moedas que será inserida no adapter
     */
    private fun setupAdapter(coins: MutableList<Coin>) {
        this.coinList = coins
        this.adapter = CoinListAdapter(this.coinList)
        adapter.setHasStableIds(true)
        this.navigateTo(this.fullListFragment)
        fullListFragment.initRecyclerView(this.adapter)
    }

    private fun updateCoin(id: Long) {
        val position = coinList.indexOf(repository.coinById(id))
        adapter.notifyItemChanged(position)
    }

    private inner class ActionReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            try {
                when (p1?.action) {
                    ACTION_FETCH_FINISHED -> {
                        getInstance()!!.fetchDataFromDatabase()
                    }
                    ACTION_COIN_UPDATED -> {
                        getInstance()!!.updateCoin(p1.getLongExtra("id", 0L))
                    }
                }
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}
