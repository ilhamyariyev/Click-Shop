package com.info.clickshop.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("load_image_url")
fun loadImage(imageView: ImageView, url: String?) {
        imageView.loadUrl(url)
}

