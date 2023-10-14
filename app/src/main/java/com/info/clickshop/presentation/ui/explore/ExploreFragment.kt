package com.info.clickshop.presentation.ui.explore

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentExploreBinding
import com.info.clickshop.domain.state.CategoryUiState
import com.info.clickshop.domain.state.SearchProductsUiState
import com.info.clickshop.presentation.ui.explore.adapter.CategoriesAdapter
import com.info.clickshop.presentation.ui.explore.adapter.SearchExploreAdapter
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel: ExploreMVVM by viewModels()
    private val categoryAdapter = CategoriesAdapter()
    private val searchAdapter = SearchExploreAdapter()

    override fun onViewCreateFinish() {
        setRecyclerView()
        searchData()
        setButtons()
    }

    override fun observeEvents() {
        with(viewModel){
            with(binding){
                searchData.observe(viewLifecycleOwner) {
                    when (it) {
                        is SearchProductsUiState.Success -> {
                            lottiLoadingSearch.gone()
                            if (it.data.isEmpty()) {
                                notFoundLayout.visible()
                                searchResultLayout.gone()
                            }
                            searchAdapter.differ.submitList(it.data)
                        }

                        is SearchProductsUiState.Error -> {
                            lottiLoadingSearch.gone()
                            notFoundLayout.visible()
                            Toast.makeText(context, "Error7", Toast.LENGTH_SHORT).show()
                        }

                        is SearchProductsUiState.Loading -> {
                            lottiLoadingSearch.visible()
                        }
                    }
                }


                categories.observe(viewLifecycleOwner) {
                    when (it) {
                        is CategoryUiState.Success -> {
                            categoryAdapter.differ.submitList(it.data)
                            if(it.data.isNotEmpty()){
                                notFoundLayout.gone()
                            }
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
            }
        }
    }

    private fun searchData(){
        with(binding){
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query?.length == 0){
                        exploreLayout.visible()
                        searchResultLayout.gone()
                        notFoundLayout.gone()
                        hideVisible()
                        searchResult()
                        false
                    }else{
                        searchResultLayout.visible()
                        exploreLayout.gone()
                        notFoundLayout.gone()
                        query?.let{ viewModel.getSearch(it) }
                        showVisible()
                        searchResult()
                        true
                    }

                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return if (newText?.length == 0){
                        searchResult()
                        exploreLayout.visible()
                        searchResultLayout.gone()
                        notFoundLayout.gone()
                        hideVisible()
                        false
                    }else{
                        searchResult()
                        searchResultLayout.visible()
                        exploreLayout.gone()
                        notFoundLayout.gone()
                        newText?.let{ viewModel.getSearch(it) }
                        showVisible()
                        true
                    }
                }
            })
        }
    }

    private fun setRecyclerView(){
        with(binding){
            rvCategories.adapter =categoryAdapter
            rvSearch.adapter = searchAdapter
        }
    }

    private fun showVisible(){
        with(binding){
            imageViewShort.visible()
            imageViewFilter.visible()
            imageViewFavorite.gone()
            imageViewNotification.gone()
        }
    }

    private fun hideVisible(){
        with(binding){
            imageViewShort.gone()
            imageViewFilter.gone()
            imageViewFavorite.visible()
            imageViewNotification.visible()
        }
    }

    private fun searchResult(){
        val result = searchAdapter.itemCount.toString()
        binding.textViewResult.text="$result : Result"
    }

    private fun setButtons(){
        binding.imageViewCategory.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.tolistcategory())
        }
        binding.imageViewShort.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.toshortBy())
        }
    }

}