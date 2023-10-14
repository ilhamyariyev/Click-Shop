package com.info.clickshop.common.util

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.info.clickshop.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Activity.showSuccessMotionToastMessage(title: String?, description: String, style: MotionToastStyle,
) {
    MotionToast.createColorToast(
        this,
        title,
        description,
        style,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        null
    )
}

fun Activity.showErrorMotionToastMessage(title: String?, description: String, style: MotionToastStyle,
) {
    MotionToast.createColorToast(
        this,
        title,
        description,
        style,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        null
    )
}

fun ImageView.loadUrl(url: String?) {
    url?.let {
        Glide
            .with(this)
            .load(url)
            .into(this);
    }
}




