package com.info.clickshop.presentation.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.R
import com.info.clickshop.common.base.BaseFragment
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.showErrorMotionToastMessage
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentFavoriteBinding
import com.info.clickshop.domain.state.FavoriteUiState
import com.info.clickshop.domain.state.ProductUiState
import com.info.clickshop.presentation.ui.favorite.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val favoriteAdapter = FavoriteAdapter()
    private val viewModel : FavoriteMVVM by viewModels()

    override fun onViewCreateFinish() {
        setRecyclerView()
        setButtons()
    }

    override fun observeEvents() {
        viewModel.favorite.observe(viewLifecycleOwner){
            with(binding){
                when (it) {
                    is FavoriteUiState.Success -> {
                        if (it.data.isEmpty()) {
                            empty.visible()
                        } else {
                            empty.gone()
                        }
                        favoriteAdapter.differ.submitList(it.data)
                        lottiLoading2.gone()
                    }
                    is FavoriteUiState.Error -> {
                        lottiLoading2.gone()
                        requireActivity().showErrorMotionToastMessage(
                            "Oops Sorry!",
                            it.message,
                            MotionToastStyle.ERROR
                        )
                    }
                    is FavoriteUiState.Loading -> {
                        lottiLoading2.visible()
                    }
                }
            }
        }
    }

    private fun setRecyclerView(){
        with(binding){
            rvFavorite.adapter =favoriteAdapter
        }
    }
    
    private fun setButtons(){
        with(binding){
            imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }
            favoriteAdapter.onDelete={
                viewModel.deleteFav(it)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            favoriteAdapter.onClick={
                findNavController().navigate(FavoriteFragmentDirections.todetail(it.toString()))
            }
        }
    }
}