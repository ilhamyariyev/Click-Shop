package com.info.clickshop.domain.mapper


import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.domain.model.CartUiModel
import com.info.clickshop.domain.model.ProductUiModel
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

    fun List<CartDTO>.toCartUiList() = map {
        CartUiModel(
            it.id,
            it.title,
            it.price,
            it.image,
            it.quantity
        )
    }
}