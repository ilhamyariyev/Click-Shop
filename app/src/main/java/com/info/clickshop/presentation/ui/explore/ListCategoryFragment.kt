package com.info.clickshop.presentation.ui.explore

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentListCategoryBinding
import com.info.clickshop.domain.state.CategoryUiState
import com.info.clickshop.presentation.ui.explore.adapter.ListCategoryAdapter
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCategoryFragment : BaseFragment<FragmentListCategoryBinding>(FragmentListCategoryBinding::inflate) {

    private val viewModel:ListCategoryMVVM by viewModels()
    private val categoryAdapter = ListCategoryAdapter()

    override fun onViewCreateFinish() {
        setRecyclerView()
        binding.imageView6.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            with(binding){
                categories.observe(viewLifecycleOwner) {
                    when (it) {
                        is CategoryUiState.Success -> {
                            categoryAdapter.differ.submitList(it.data)
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

    private fun setRecyclerView(){
        with(binding){
            rvListCategory.adapter =categoryAdapter
        }
    }

}