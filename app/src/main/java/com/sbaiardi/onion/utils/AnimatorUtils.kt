package com.sbaiardi.onion.utils

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class AnimatorUtils {
    companion object{
        public fun startCountAnimationInt(textView: TextView, value: Int ) {
            val animator = ValueAnimator.ofInt(0, value)
            animator.duration = 2500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation -> textView.text = (String.format("%,2d",animation.animatedValue)) }
            animator.start()
        }

        public fun startCountAnimationDouble(textView: TextView, value: Float ) {
            val animator = ValueAnimator.ofFloat(0f, value)
            animator.duration = 2500
            animator.interpolator = FastOutSlowInInterpolator()
            animator.addUpdateListener { animation -> textView.text = (String.format("%.2f",animation.animatedValue))+"%" }
            animator.start()
        }
    }
}