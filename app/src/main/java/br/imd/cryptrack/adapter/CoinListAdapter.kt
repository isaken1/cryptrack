package br.imd.cryptrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.imd.cryptrack.R
import br.imd.cryptrack.model.Coin
import kotlinx.android.synthetic.main.coin_item.view.*

class CoinListAdapter(
    private val coins: MutableList<Coin>): RecyclerView.Adapter<CoinListAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.coinLogo
        val txtName: TextView = itemView.coinName
        val txtSymbol: TextView = itemView.coinSymbol
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.coin_item, parent, false)

        val viewHolder = MyViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, _, _, name, _, _, symbol)
                = coins[position]

        holder.txtName.setText(name)
        holder.txtSymbol.setText(symbol)
    }

    override fun getItemId(position: Int): Long = coins[position].id



}