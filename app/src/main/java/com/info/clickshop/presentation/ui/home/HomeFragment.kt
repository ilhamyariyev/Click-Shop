package com.info.clickshop.presentation.ui.home

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.info.clickshop.R
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentHomeBinding
import com.info.clickshop.domain.state.CategoryUiState
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.SearchProductsUiState
import com.info.clickshop.presentation.ui.home.adapter.AllDummyProductsAdapter
import com.info.clickshop.presentation.ui.home.adapter.CategoryAdapter
import com.info.clickshop.presentation.ui.home.adapter.FlashSaleAdapter
import com.info.clickshop.presentation.ui.home.adapter.MegaSaleAdapter
import com.info.clickshop.presentation.ui.home.adapter.PagerAdapter
import com.info.clickshop.presentation.ui.home.adapter.SearchAdapter
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeMVVM by viewModels()
    private val getFlashSaleAdapter = FlashSaleAdapter()
    private val getMegaSaleAdapter = MegaSaleAdapter()
    private val top5ProductAdapter =PagerAdapter()
    private val allProductsAdapter = AllDummyProductsAdapter()
    private val searchAdapter = SearchAdapter()
    private val adapter = CategoryAdapter()
    private lateinit var handler:Handler
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreateFinish() {
        setRecyclerView()
        init()
        setUpTransformer()
        setButtons()
        saveTimeinSP()
        searchData()

    }

    override fun observeEvents() {
        with(viewModel){
            with(binding) {
                searchData.observe(viewLifecycleOwner) {
                    when (it) {
                        is SearchProductsUiState.Success -> {
                            lottiLoadingSearch.gone()
                            searchAdapter.differ.submitList(it.data)
                            if (it.data.isEmpty()) {
                                notfound.visible()
                            } else {
                                notfound.gone()
                            }
                        }

                        is SearchProductsUiState.Error -> {
                            lottiLoadingSearch.gone()
                            notfound.visible()
                            Toast.makeText(context, "Error7", Toast.LENGTH_SHORT).show()
                        }

                        is SearchProductsUiState.Loading -> {
                            lottiLoadingSearch.visible()
                        }
                    }
                }

                top5Products.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            lottiLoadingPager.gone()
                            top5ProductAdapter.differ.submitList(it.data.products)
                        }

                        is DummyProductsUiState.Error -> {
                            lottiLoadingPager.gone()
                            Toast.makeText(context, "Error1", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                            lottiLoadingPager.visible()
                        }
                    }
                }

                categories.observe(viewLifecycleOwner) {
                    when (it) {
                        is CategoryUiState.Success -> {
                            adapter.differ.submitList(it.data)
                            lottiLoadingCategory.gone()
                        }

                        is CategoryUiState.Error -> {
                            lottiLoadingCategory.gone()
                            Toast.makeText(context, "Error2", Toast.LENGTH_SHORT).show()
                        }

                        is CategoryUiState.Loading -> {
                            lottiLoadingCategory.visible()
                        }
                    }
                }

                top10DummyProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            lottiLoadingFlash.gone()
                            getFlashSaleAdapter.differ.submitList(it.data.products)
                        }

                        is DummyProductsUiState.Error -> {
                            lottiLoadingFlash.gone()
                            Toast.makeText(context, "Error3", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                            lottiLoadingFlash.visible()
                        }
                    }
                }

                allDummyProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            allProductsAdapter.differ.submitList(it.data.products)
                        }

                        is DummyProductsUiState.Error -> {
                            Toast.makeText(context, "Error4", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                        }
                    }
                }

                allProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            lottiLoadingMega.gone()
                            getMegaSaleAdapter.differ.submitList(it.data.products)
                        }

                        is DummyProductsUiState.Error -> {
                            lottiLoadingMega.gone()
                            Toast.makeText(context, "Error5", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                            lottiLoadingMega.visible()
                        }
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        with(binding) {
            FlashSaleRv.adapter = getFlashSaleAdapter
            FlashSaleRv.apply {
                set3DItem(true)
                setAlpha(true)
                setInfinite(true)
            }
            MegaSaleRv.adapter =getMegaSaleAdapter
            MegaSaleRv.apply {
                set3DItem(true)
                setAlpha(true)
                setInfinite(true)
            }
            viewPager.adapter = top5ProductAdapter
            categoryRv.adapter=adapter
            allproductsRv.adapter=allProductsAdapter
            rvSearch.adapter = searchAdapter
        }
    }

    private fun searchData(){
        with(binding){
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query?.length == 0){
                        layoutMain.visible()
                        layoutSearch.gone()
                        notfound.gone()
                        false
                    }else{
                        query?.let{ viewModel.getSearch(it) }
                        layoutMain.gone()
                        notfound.gone()
                        layoutSearch.visible()
                        true
                    }

                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return if (newText?.length == 0){
                        layoutMain.visible()
                        layoutSearch.gone()
                        notfound.gone()
                        false
                    }else{
                        newText?.let{ viewModel.getSearch(it) }
                        layoutMain.gone()
                        notfound.gone()
                        layoutSearch.visible()
                        true
                    }
                }
            })
        }
    }

    private fun setButtons(){
        with(binding){
            textViewSeeMoreFlash.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.toflash())
            }
            textViewSeeMoreMega.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.tomega())
            }
            imageViewFavorite.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.tofavorite())
            }
        }

    }

    private fun saveTimeinSP(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val savedTime = sharedPreferences.getLong("saved_time1", 0)
        if (savedTime != 0L) {
            val currentTime = System.currentTimeMillis()
            val remainingTime = savedTime - currentTime
            binding.countdown.start(remainingTime)
        }
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
        val currentTime = System.currentTimeMillis()
        val remainingTime = binding.countdown.remainTime
        val savedTime = currentTime + remainingTime
        sharedPreferences.edit().putLong("saved_time1", savedTime).apply()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }

    private val runnable= Runnable {
        with(binding){
            if (viewPager.currentItem < top5ProductAdapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                viewPager.currentItem = 0
            }
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY=0.85f + r + 0.14f
        }
        binding.viewPager.setPageTransformer(transformer)
    }

    private fun init() {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        handler= Handler(Looper.myLooper()!!)
        with(binding){
            viewPager.animate().scaleX(1f)
            viewPager.clipToPadding=false
            viewPager.clipChildren=false
            viewPager.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable,2000)
                }
            })
            dotsIndicator.setViewPager2(binding.viewPager)
            countdown.start(86400000)
        }
    }
}