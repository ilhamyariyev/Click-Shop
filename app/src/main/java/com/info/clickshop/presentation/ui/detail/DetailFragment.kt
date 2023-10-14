package com.info.clickshop.presentation.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.info.clickshop.R
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.FragmentDetailBinding
import com.info.clickshop.domain.state.CommentsUiState
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.ProductUiState
import com.info.clickshop.presentation.ui.detail.adapter.AlsoLikeAdapter
import com.info.clickshop.presentation.ui.detail.adapter.ColorsAdapter
import com.info.clickshop.presentation.ui.detail.adapter.CommentAdapter
import com.info.clickshop.presentation.ui.detail.adapter.SizesAdapter
import com.info.clickshop.common.base.BaseFragment
import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.domain.mapper.Mapper.toCartUiModel
import com.info.clickshop.domain.mapper.Mapper.toProductUiModel
import com.info.clickshop.domain.model.CartUiModel
import com.info.clickshop.domain.model.ProductUiModel
import com.info.clickshop.domain.state.CartUiState
import com.info.clickshop.presentation.ui.detail.adapter.ColorItem
import com.info.clickshop.presentation.ui.detail.adapter.SizeItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel:DetailMVVM by viewModels()
    private val args by navArgs<DetailFragmentArgs>()
    private val alsoLikeAdapter = AlsoLikeAdapter()
    private val commentsAdapter = CommentAdapter()
    private val colorsAdapter = ColorsAdapter()
    private val sizesAdapter = SizesAdapter()
   // private lateinit var product : Product
    private lateinit var mProduct: ProductUiModel
   // private lateinit var cProduct: CartUiModel


    override fun onViewCreateFinish() {
        observeEvents()
        getData(args.id.toInt())
        setRecyclerView()
        setColors()
        setSizes()
        setButtons()
    }

    override fun observeEvents() {
        with(viewModel){
            with(binding){
                detailProduct.observe(viewLifecycleOwner){
                    when (it) {
                        is ProductUiState.Success -> {
                            detail = it.data
                            mProduct=it.data.toProductUiModel()
                            //cProduct=it.data.toCartUiModel()
                            ratingBar2.rating = it.data.rating.toFloat()
                            ratingBar3.rating = it.data.rating.toFloat()
                            lottiLoading.gone()
                        }

                        is ProductUiState.Error -> {
                            Toast.makeText(context, "Error download details", Toast.LENGTH_SHORT).show()
                            lottiLoading.gone()
                        }

                        is ProductUiState.Loading -> {
                            lottiLoading.visible()
                        }

                    }
                }

                alsoLikeProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is DummyProductsUiState.Success -> {
                            //lottiLoadingMega.gone()
                            alsoLikeAdapter.differ.submitList(it.data.products)

                        }

                        is DummyProductsUiState.Error -> {
                            //lottiLoadingMega.gone()
                            Toast.makeText(context, "Error5", Toast.LENGTH_SHORT).show()
                        }

                        is DummyProductsUiState.Loading -> {
                            //lottiLoadingMega.visible()
                        }
                    }
                }

                comments.observe(viewLifecycleOwner) {
                    when (it) {
                        is CommentsUiState.Success -> {
                            commentsAdapter.differ.submitList(it.data.comments)
                        }

                        is CommentsUiState.Error -> {
                            Toast.makeText(context, "ErrorComment", Toast.LENGTH_SHORT).show()
                        }

                        is CommentsUiState.Loading -> {}
                    }
                }

            }


        }


    }

    private fun setButtons(){
        with(binding){
            var isFavorite=false
            imageViewFavorite2.setOnClickListener {
                if (isFavorite){
                    viewModel.deleteData(mProduct)
                    Toast.makeText(context, "${mProduct.title} deleted from Favorite Products", Toast.LENGTH_SHORT).show()

                }else{
                    viewModel.addFavorite(mProduct)
                    Toast.makeText(context, "${mProduct.title} saved in Favorite Products", Toast.LENGTH_SHORT).show()
                }

                val imageRes = if (isFavorite){
                    R.drawable.love
                }else{
                    R.drawable.baseline_favorite_24
                }
                imageViewFavorite2.setImageResource(imageRes)
                isFavorite=!isFavorite

            }

            imageViewback.setOnClickListener {
                findNavController().navigateUp()
            }


            buttonCart.setOnClickListener {
                buttonCart.startAnimation()
                if (isAdded){
                    lifecycleScope.launch {
                        delay(1000)
                        buttonCart.revertAnimation()
                        viewModel.addCartProduct(mProduct)
                        Toast.makeText(context, "product was added to Cart", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    buttonCart.revertAnimation()
                    Toast.makeText(context, "A problem occurred while saving", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun getData(id: Int) {
        viewModel.getDetailProduct(id)
    }

    private fun setRecyclerView() {
        with(binding) {
            rvAlsoLike.adapter = alsoLikeAdapter
            rvComment.adapter=commentsAdapter
            rvColor.adapter = colorsAdapter
            rvSize.adapter =sizesAdapter
         }
    }

    private fun setColors(){
        val colorsList = mutableListOf(
            ColorItem("#FF5733"),
            ColorItem("#FFC300"),
            ColorItem("#33FF6B"),
            ColorItem("#334BFF"),
            ColorItem("#A733FF"),
            ColorItem("#000000"),
            ColorItem("#00FF00"),
            ColorItem("#FFFF00"),
            ColorItem("#FFA500"),
            ColorItem("#808080"),
        )
        colorsAdapter.setColors(colorsList)
    }

    private fun setSizes(){
        val numbersList = mutableListOf(
            SizeItem("1"),
            SizeItem("2"),
            SizeItem("3"),
            SizeItem("4"),
            SizeItem("5"),
            SizeItem("6"),
            SizeItem("7"),
            SizeItem("8"),
            SizeItem("9"),
        )
        sizesAdapter.setSizes(numbersList)
    }
}

