package br.imd.cryptrack.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.imd.cryptrack.R
import br.imd.cryptrack.adapter.CoinListAdapter
import kotlinx.android.synthetic.main.fragment_full_list.view.*

class FullListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var searchView: SearchView? = null
    private lateinit var queryListener: SearchView.OnQueryTextListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_full_list, container, false)
        recyclerView = view.rvFullList
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.full_list_toolbar, menu)
        val searchItem = menu.findItem(R.id.action_search)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryListener = object: SearchView.OnQueryTextListener {
                //TODO: Mostrar apenas as moedas com nome contido no conteúdo da pesquisa
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.i("onQueryTextSubmit", query ?: "" )
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.i("onQueryTextChange", newText ?: "")
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun initRecyclerView(adapter: CoinListAdapter) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                return false
            }
        }
        searchView?.setOnQueryTextListener(queryListener)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FullListFragment()
    }

}
