package com.sbaiardi.onion.utils

import android.view.View
import com.airbnb.lottie.LottieAnimationView

class LottieAnimationUtils {
    companion object{
        public fun loadLottieAnim(perLast: Int, perBeforeLast: Int, up: LottieAnimationView, down: LottieAnimationView){
            if (perLast > perBeforeLast) {
                up.visibility = View.VISIBLE
                down.visibility = View.GONE
            }
            else {
                up.visibility = View.GONE
                down.visibility = View.VISIBLE
            }
        }

        public fun loadLottieAnimDouble(perLast: Double, perBeforeLast: Double, up: LottieAnimationView, down: LottieAnimationView){
            if (perLast > perBeforeLast) {
                up.visibility = View.VISIBLE
                down.visibility = View.GONE
            }
            else {
                up.visibility = View.GONE
                down.visibility = View.VISIBLE
            }
        }
    }
}