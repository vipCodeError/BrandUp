package com.vipcodeerror.brandup.util

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class ValueAnimatorUtil {

    companion object {

        fun valueAnimatorScaleUtil(firstValue : Float, secondValue : Float, view : View){
            val valueAnimator = ValueAnimator.ofFloat(firstValue, secondValue)
            valueAnimator.addUpdateListener {
                var value = it.animatedValue as Float
                view.scaleX = value
                view.scaleY = value
                // view.x = value
            }

            valueAnimator.duration = 300
            valueAnimator.interpolator = AccelerateDecelerateInterpolator()
            valueAnimator.start()
        }

    }
}