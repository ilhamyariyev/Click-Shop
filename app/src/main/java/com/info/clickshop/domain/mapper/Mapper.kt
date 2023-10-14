package com.info.clickshop.domain.mapper


import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.domain.model.CartUiModel
import com.info.clickshop.domain.model.FavoriteUiModel
import com.info.clickshop.domain.model.ProductUiModel
import com.info.clickshop.presentation.ui.detail.adapter.ColorItem
import java.util.Locale

object Mapper {

    fun Product.toProductUiModel() =
        ProductUiModel(
            id,
            title.capitalize(Locale.ROOT),
            description,
            rating.toInt(),
            discountPercentage,
            images,
            price,
            price / (1 - (discountPercentage / 100)),
            stock,
            category,
            brand,
            thumbnail

        )

    fun Product.toCartUiModel() =
        CartUiModel(
            id,
            title.capitalize(Locale.ROOT),
            price,
            images[0],
            1

        )

    fun ProductUiModel.toFavoriteDTO() =
        FavoriteDTO(
            id,
            title,
            rating,
            price,
            discount.toInt(),
            images[0]
        )

    fun ProductUiModel.toCartDTO() =
        CartDTO(
            id,
            title,
            price,
            images[0],
            1
        )


//    fun CartUiModel.toCartDTO() =
//        CartDTO(
//            id,
//            title,
//            price,
//            image,
//            quantity
//        )


    fun FavoriteUiModel.toFavoriteDTO() =
        FavoriteDTO(
            id,
            title,
            rating,
            price,
            discount,
            image
        )

    fun List<FavoriteDTO>.toFavUiModelList() = map {
        FavoriteUiModel(
            it.id,
            it.title,
            it.rating,
            it.price,
            it.discount,
            it.image
        )
    }

    fun List<CartDTO>.toCartUiList() = map {
        CartUiModel(
            it.id,
            it.title,
            it.price,
            it.image,
            it.quantity
        )
    }



    fun List<Product>.toProductUiList() = map {
        ProductUiModel(
            it.id,
            it.title.capitalize(Locale.ROOT),
            it.description,
            it.rating.toInt(),
            it.discountPercentage,
            it.images,
            it.price,
            it.price / (1 - (it.discountPercentage / 100)),
            it.stock,
            it.category,
            it.brand,
            it.thumbnail
        )
    }

}