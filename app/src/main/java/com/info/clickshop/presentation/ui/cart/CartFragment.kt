package com.info.clickshop.presentation.ui.cart

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.common.base.BaseFragment
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.showErrorMotionToastMessage
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentCartBinding
import com.info.clickshop.domain.mapper.Mapper.toCartUiList
import com.info.clickshop.domain.model.CartUiModel
import com.info.clickshop.domain.state.CartUiState
import com.info.clickshop.presentation.ui.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val cartAdapter = CartAdapter()
    private val viewModel : CartMVVM by viewModels()

    override fun onViewCreateFinish() {
        observeEvents()
        setRecyclerView()
        setButtons()
    }

    override fun observeEvents() {
        viewModel.cart.observe(viewLifecycleOwner){
            with(binding){
                when (it) {
                    is CartUiState.Success -> {
                        if (it.data.isEmpty()) {
                            empty.visible()
                            Cart.gone()
                        } else {
                            empty.gone()
                            Cart.visible()
                        }
                        cartAdapter.differ.submitList(it.data)
                        binding.size = it.data.size
                        lottiLoading4.gone()
                    }
                    is CartUiState.Error -> {
                        lottiLoading4.gone()
                        requireActivity().showErrorMotionToastMessage(
                            "Oops Sorry!",
                            it.message,
                            MotionToastStyle.ERROR
                        )
                    }
                    is CartUiState.Loading -> {
                        lottiLoading4.visible()
                    }
                }
            }
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.total = it.toDouble()
        }
    }

    private fun setRecyclerView(){
        with(binding){
            rvCart.adapter =cartAdapter
        }
    }

    private fun setButtons(){
            cartAdapter.onDelete={
                viewModel.deleteCart(it)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            cartAdapter.onClick={
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToDetailFragment(it.toString()))
            }
            cartAdapter.onClickMinus = {
                viewModel.decreasePrice(it)
            }
            cartAdapter.onClickPlus = {
                viewModel.increasePrice(it)
            }
    }

}