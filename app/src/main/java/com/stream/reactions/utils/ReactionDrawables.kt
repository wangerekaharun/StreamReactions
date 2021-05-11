package com.stream.reactions.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.stream.reactions.R
import io.getstream.chat.android.ui.SupportedReactions

fun likeDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}

fun clapDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_clapping)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_clapping)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}

fun wonderingDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_wondering)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_wondering)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}

fun brilliantDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_brilliant)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_brilliant)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}

fun handShakeDrawable(context: Context): SupportedReactions.ReactionDrawable {
    val drawableInactive = ContextCompat.getDrawable(context, R.drawable.ic_hand_shake)!!
    val drawableActive = ContextCompat.getDrawable(context, R.drawable.ic_hand_shake)!!.apply {
        setTint(ContextCompat.getColor(context,android.R.color.holo_red_dark ))
    }
    return SupportedReactions.ReactionDrawable(drawableInactive, drawableActive)
}