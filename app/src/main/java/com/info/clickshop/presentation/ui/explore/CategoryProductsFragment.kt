package com.info.clickshop.presentation.ui.explore

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentCategoryProductsBinding
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.presentation.ui.explore.adapter.CategoryProductsAdapter
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryProductsFragment : BaseFragment<FragmentCategoryProductsBinding>(FragmentCategoryProductsBinding::inflate) {

    private val viewModel:CategoryProductsMVVM by viewModels()
    private val args:CategoryProductsFragmentArgs by navArgs()
    private val productsAdapter = CategoryProductsAdapter()

    override fun onViewCreateFinish() {
        setRecyclerView()
        getData(args.category)
    }

    override fun observeEvents() {
        with(viewModel){
            with(binding){
                categoryProduct.observe(viewLifecycleOwner){
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            productsAdapter.differ.submitList(it.data.products)
                            lottiLoadingSearch.gone()
                        }

                        is DummyProductsUiState.Error -> {
                            Toast.makeText(context, "Error download details", Toast.LENGTH_SHORT).show()
                            lottiLoadingSearch.gone()
                        }

                        is DummyProductsUiState.Loading -> {
                            lottiLoadingSearch.visible()
                        }

                    }
                }
            }

        }

    }

    private fun getData(category:String) {
        viewModel.getCategoryProduct(category)
        binding.textViewCategory.text=args.category
    }

    private fun setRecyclerView() {
        with(binding) {
            rvSearch.adapter = productsAdapter
        }
    }

}