package com.info.clickshop.presentation.ui.seemore

import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.loadUrl
import com.info.clickshop.common.util.visible
import com.info.clickshop.data.dto.Product
import com.info.clickshop.databinding.FragmentSeeMoreFlashSaleBinding
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.ProductUiState
import com.info.clickshop.presentation.ui.home.adapter.AllDummyProductsAdapter
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeMoreFlashFragment : BaseFragment<FragmentSeeMoreFlashSaleBinding>(FragmentSeeMoreFlashSaleBinding::inflate) {

    private lateinit var product:Product
    private val allProductsAdapter = AllDummyProductsAdapter()
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel:SeeMoreFlashMVVM by viewModels()

    override fun onViewCreateFinish() {
        setRecyclerView()
        saveTimeinSP()
        binding.countdown.start(86400000)
    }

    override fun observeEvents() {
        with(viewModel){
            with(binding) {
                singleProduct.observe(viewLifecycleOwner) {
                    when (it) {
                        is ProductUiState.Success -> {
                            lottiLoadingPage.gone()
                            product = it.data
                            binding.imageViewSingle.loadUrl(product.thumbnail)
                        }

                        is ProductUiState.Error -> {
                            lottiLoadingPage.gone()
                            Toast.makeText(context, "Error4", Toast.LENGTH_SHORT).show()
                        }

                        is ProductUiState.Loading -> {
                            lottiLoadingPage.visible()
                        }
                    }
                }

                allDummyProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            lottiLoading3.gone()
                            allProductsAdapter.differ.submitList(it.data.products)
                        }

                        is DummyProductsUiState.Error -> {
                            lottiLoading3.gone()
                            Toast.makeText(context, "Error4", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                            lottiLoading3.visible()
                        }
                    }
                }
            }
        }
    }
    private fun saveTimeinSP(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val savedTime = sharedPreferences.getLong("saved_time2", 0)
        if (savedTime != 0L) {
            val currentTime = System.currentTimeMillis()
            val remainingTime = savedTime - currentTime
            binding.countdown.start(remainingTime)
        }
    }

    override fun onPause() {
        super.onPause()
        val currentTime = System.currentTimeMillis()
        val remainingTime = binding.countdown.remainTime
        val savedTime = currentTime + remainingTime
        sharedPreferences.edit().putLong("saved_time2", savedTime).apply()
    }

    private fun setRecyclerView() {
        with(binding) {
            recyclerView.adapter=allProductsAdapter
        }
    }
}