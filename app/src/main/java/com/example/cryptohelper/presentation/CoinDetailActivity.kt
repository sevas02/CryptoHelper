package com.example.cryptohelper.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cryptohelper.R
import com.example.cryptohelper.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) { coinInfo ->
            with(binding) {
                tvPrice.text = coinInfo.price
                tvMinPrice.text = coinInfo.lowDay
                tvMaxPrice.text = coinInfo.highDay
                tvLastMarket.text = coinInfo.lastMarket
                tvLastUpdate.text = coinInfo.lastUpdate
                textViewFromCoin.text = coinInfo.fromSymbol
                textViewToCoin.text = coinInfo.toSymbol
                Picasso.get().load(coinInfo.imageUrl).into(imageViewCoinDetail)
            }
        }

    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}