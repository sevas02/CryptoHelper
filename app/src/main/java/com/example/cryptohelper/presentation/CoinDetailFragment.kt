package com.example.cryptohelper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptohelper.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel
    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null!")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) { coinInfo ->
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getSymbol() = requireArguments().getString(EXTRA_FROM_SYMBOL, "")

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}