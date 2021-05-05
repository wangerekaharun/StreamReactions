package com.stream.reactions

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("reactionImage")
fun setImageViewResource(view: ImageView, resId : Int) {
    view.setImageResource(resId)
}