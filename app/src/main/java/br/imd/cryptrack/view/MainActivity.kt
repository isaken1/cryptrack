package br.imd.cryptrack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import br.imd.cryptrack.R
import br.imd.cryptrack.adapter.CoinListAdapter
import br.imd.cryptrack.model.Coin
import br.imd.cryptrack.model.CryptoCompareCoinListResponse
import br.imd.cryptrack.retrofit.RetrofitInitializer
import br.imd.cryptrack.view.fragment.FullListFragment
import br.imd.cryptrack.view.fragment.LoadingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val navigator: BottomNavigationView by lazy { nav }
    private val fullListFragment =  FullListFragment.newInstance()
    private val loadingFragment: LoadingFragment = LoadingFragment.newInstance()
    private lateinit var adapter: CoinListAdapter
    private var coinList: MutableList<Coin> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.app_name)
        // TODO: Adicionar comportamento para navegar para a tela de favoritos por padrão caso haja
        //  pelo menos uma moeda cadastrada como favorita. Caso contrário, mostrar a lista inteira.
        navigator.setOnNavigationItemSelectedListener(this)

        val params = navigator.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = BottomNavigationBehavior()

        val call = RetrofitInitializer().cryptoCompareService().getAllCoins()
        navigateTo(loadingFragment)

        call.enqueue(object: Callback<CryptoCompareCoinListResponse> {
            override fun onResponse(
                call: Call<CryptoCompareCoinListResponse>,
                response: Response<CryptoCompareCoinListResponse>
            ) {
                Toast.makeText(this@MainActivity, response.body()?.response,
                    Toast.LENGTH_SHORT).show()
                this@MainActivity.coinList = response.body()!!.data
                this@MainActivity.adapter = CoinListAdapter(coinList)
                this@MainActivity.navigateTo(this@MainActivity.fullListFragment)
                fullListFragment.initRecyclerView(adapter)
            }


            override fun onFailure(call: Call<CryptoCompareCoinListResponse>, t: Throwable) {
                Log.e("ERROR", "Erro ao acessar a API", t)
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                //call.enqueue(this)
            }
        })
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
     */
    private fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commitNow()
    }
}
