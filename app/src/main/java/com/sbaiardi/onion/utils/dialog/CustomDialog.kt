package com.sbaiardi.onion.utils.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import com.sbaiardi.onion.R
import com.sbaiardi.onion.utils.dialog.title
import kotlinx.android.synthetic.main.custom_dialog_layout.*

class CustomDialog {
        /***
         * Positions For Alert Dialog
         * */
        enum class POSITIONS {
            CENTER, BOTTOM
        }

        companion object {

            private lateinit var layoutInflater: LayoutInflater

            /***
             * core method For Alert Dialog
             * */
            fun build(
                context: FragmentActivity?
            ): AlertDialog {
                layoutInflater = LayoutInflater.from(context)
                val alertDialog =
                    AlertDialog.Builder(
                        context, R.style.full_screen_dialog
                    )
                        .setView(R.layout.custom_dialog_layout)
                val alert: AlertDialog = alertDialog.create()
                // Let's start with animation work. We just need to create a style and use it here as follows.
                //Pop In and Pop Out Animation yet pending
//            alert.window?.attributes?.windowAnimations = R.style.SlidingDialogAnimation
                alert.show()
                return alert
            }
        }
    }

    /***
     * Title Properties For Alert Dialog
     * */
    fun AlertDialog.title(
        title: String,
        fontStyle: Typeface? = null,
        titleColor: Int = 0
    ): AlertDialog {
        this.title_head.text = title.trim()
        if (fontStyle != null) {
            this.title_head.typeface = fontStyle
        }
        if (titleColor != 0) {
            this.title_head.setTextColor(titleColor)
        }
        this.title_head.show()
        return this
    }

    /***
     * Dialog Background properties For Alert Dialog
     * */
    fun AlertDialog.background(
        dialogBackgroundColor: Int? = null
    ): AlertDialog {
        if (dialogBackgroundColor != null) {
            this.mainLayout.setBackgroundResource(dialogBackgroundColor)
        }
        return this
    }

    /***
     * Positions of Alert Dialog
     * */
    fun AlertDialog.position(
        position: CustomDialog.POSITIONS = CustomDialog.POSITIONS.BOTTOM
    ): AlertDialog {
        val layoutParams = mainLayout.layoutParams as RelativeLayout.LayoutParams
        if (position == CustomDialog.POSITIONS.CENTER) {
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        } else if (position == CustomDialog.POSITIONS.BOTTOM) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        }
        mainLayout!!.layoutParams = layoutParams
        return this
    }

    /***
     * Sub Title or Body of Alert Dialog
     * */
    fun AlertDialog.body(
        body: String,
        fontStyle: Typeface? = null,
        color: Int = 0
    ): AlertDialog {
        this.subHeading.text = body.trim()
        this.subHeading.show()
        if (fontStyle != null) {
            this.subHeading.typeface = fontStyle
        }
        if (color != 0) {
            this.subHeading.setTextColor(color)
        }
        return this
    }

    /***
     * Icon of  Alert Dialog
     * */
    fun AlertDialog.icon(
        icon: Int,
        animateIcon: Boolean = false
    ): AlertDialog {
        this.image.setImageResource(icon)
        this.image.show()
        // Pulse Animation for Icon
        if (animateIcon) {
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
            image.startAnimation(pulseAnimation)
        }
        return this
    }

    /***
     * onPositive Button Properties For Alert Dialog
     *
     * No Need to call dismiss(). It is calling already
     * */
    fun AlertDialog.onFirstChoise(text: String,
                      buttonBackgroundColor: Int? = null,
                      textColor: Int? = null,
                      action: (() -> Unit)? = null
    ): AlertDialog {
        this.firstChoise.show()
        if (buttonBackgroundColor != null) {
            this.firstChoise.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            this.firstChoise.setTextColor(textColor)
        }
        this.firstChoise.text = text.trim()
        this.firstChoise.setOnClickListener {
            action?.invoke()
            dismiss()
        }
        return this
    }

    fun AlertDialog.onSecondChoise(text: String,
                                  buttonBackgroundColor: Int? = null,
                                  textColor: Int? = null,
                                  action: (() -> Unit)? = null
    ): AlertDialog {
        this.secondChoise.show()
        if (buttonBackgroundColor != null) {
            this.secondChoise.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            this.secondChoise.setTextColor(textColor)
        }
        this.secondChoise.text = text.trim()
        this.secondChoise.setOnClickListener {
            action?.invoke()
            dismiss()
        }
        return this
    }

    fun AlertDialog.onThirdChoise(text: String,
                                   buttonBackgroundColor: Int? = null,
                                   textColor: Int? = null,
                                   action: (() -> Unit)? = null
    ): AlertDialog {
        this.thirdChoise.show()
        if (buttonBackgroundColor != null) {
            this.thirdChoise.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            this.thirdChoise.setTextColor(textColor)
        }
        this.thirdChoise.text = text.trim()
        this.thirdChoise.setOnClickListener {
            action?.invoke()
            dismiss()
        }
        return this
    }

    fun AlertDialog.onPositive(
        text: String,
        buttonBackgroundColor: Int? = null,
        textColor: Int? = null,
        action: (() -> Unit)? = null
    ): AlertDialog {
        this.yesButton.show()
        if (buttonBackgroundColor != null) {
            this.yesButton.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            this.yesButton.setTextColor(textColor)
        }
        this.yesButton.text = text.trim()
        this.yesButton.setOnClickListener {
            action?.invoke()
            dismiss()
        }
        return this
    }

    /***
     * onNegative Button Properties For Alert Dialog
     *
     * No Need to call dismiss(). It is calling already
     * */
    fun AlertDialog.onNegative(
        text: String,
        buttonBackgroundColor: Int? = null,
        textColor: Int? = null,
        action: (() -> Unit)? = null
    ): AlertDialog {
        this.noButton.show()
        this.noButton.text = text.trim()
        if (textColor != null) {
            this.noButton.setTextColor(textColor)
        }
        if (buttonBackgroundColor != null) {
            this.noButton.setBackgroundResource(buttonBackgroundColor)
        }
        this.noButton.setOnClickListener {
            action?.invoke()
            dismiss()
        }
        return this
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

