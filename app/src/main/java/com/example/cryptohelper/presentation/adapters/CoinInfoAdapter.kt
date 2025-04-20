package com.example.cryptohelper.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohelper.R
import com.example.cryptohelper.databinding.ItemCoinInfoBinding
import com.example.cryptohelper.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                textViewSymbols.text =
                    String.format(symbolsTemplate, fromSymbol, toSymbol)
                textViewPrice.text = price.toString()
                textViewLastUpdate.text =
                    String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(imageViewCoinIcon)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(coin)
                }
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
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}