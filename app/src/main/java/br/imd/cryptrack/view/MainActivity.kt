package br.imd.cryptrack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.imd.cryptrack.R
import br.imd.cryptrack.model.CryptoCompareCoinListResponse
import br.imd.cryptrack.retrofit.RetrofitInitializer
import br.imd.cryptrack.view.fragment.FullListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_full_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var navigator: BottomNavigationView? = null
    private var fullListFragment: FullListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Adicionar comportamento para navegar para a tela de favoritos por padrão caso haja
        //  pelo menos uma moeda cadastrada como favorita. Caso contrário, mostrar a lista inteira.
        navigator = nav
        navigator!!.setOnNavigationItemSelectedListener(this)
        navigateTo(FullListFragment())

        val call = RetrofitInitializer().cryptoCompareService().getAllCoins()

        call.enqueue(object: Callback<CryptoCompareCoinListResponse> {

            override fun onResponse(
                call: Call<CryptoCompareCoinListResponse>,
                response: Response<CryptoCompareCoinListResponse>
            ) {
                response.body()?.let {
//                    this@MainActivity.txtTeste.setText(it.message)
                }
            }

            override fun onFailure(call: Call<CryptoCompareCoinListResponse>, t: Throwable) {
                Log.e("ERROR", "Erro ao acessar a API", t)
//                this@MainActivity.txtTeste.setText(t.message)
            }
        })

        fullListFragment = FullListFragment()
    }

    /**
     * Função necessária para implementar a navegação através do BottomNavigationView.
     * Similar ao onClick dos botões
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_list_all -> {
                if (fullListFragment == null) {
                    fullListFragment = FullListFragment()
                }
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
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
