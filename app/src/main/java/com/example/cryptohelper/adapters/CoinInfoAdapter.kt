package com.example.cryptohelper.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohelper.R
import com.example.cryptohelper.databinding.ItemCoinInfoBinding
import com.example.cryptohelper.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = arrayListOf<CoinPriceInfo>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            val symbolsTemplate = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
            textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            textViewPrice.text = coin.price.toString()
            textViewLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
            Picasso.get().load(coin.getFullImageUrl()).into(imageViewCoinIcon)
            itemView.setOnClickListener {
                onCoinClickListener?.onCoinClick(coin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(binding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textViewSymbols = binding.textViewSymbols
        val textViewPrice = binding.textViewPrice
        val textViewLastUpdate = binding.textViewLastUpdate
        val imageViewCoinIcon = binding.imageViewCoinIcon
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}