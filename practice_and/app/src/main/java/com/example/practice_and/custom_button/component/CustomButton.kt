package com.example.practice_and.custom_button.component

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.example.practice_and.R
import com.example.practice_and.databinding.LayoutCustomButtonBinding

sealed class ButtonStyle(val value: Int) {
    object Primary: ButtonStyle(1)
    object PrimaryLine: ButtonStyle(2)
    object GrayLine: ButtonStyle(3)
    object TextOnly: ButtonStyle(4)
}
private fun Int.toButtonStyle() : ButtonStyle {
    return when(this) {
        1 -> ButtonStyle.Primary
        2 -> ButtonStyle.PrimaryLine
        3 -> ButtonStyle.GrayLine
        4 -> ButtonStyle.TextOnly
        else -> throw IllegalArgumentException("This value is not supported for ButtonStyle: $this")
    }
}

sealed class ButtonSize(val value: Int) {
    object Large: ButtonSize(1)
    object Middle: ButtonSize(2)
    object Small: ButtonSize(3)
}
private fun Int.toButtonSize() : ButtonSize {
    return when(this) {
        1 -> ButtonSize.Large
        2 -> ButtonSize.Middle
        3 -> ButtonSize.Small
        else -> throw IllegalArgumentException("This value is not supported for ButtonSize: $this")
    }
}

sealed class ButtonIconStyle(val value: Int) {
    object None: ButtonIconStyle(1)
    object LeftIcon: ButtonIconStyle(2)
    object RightIcon: ButtonIconStyle(3)
}
private fun Int.toButtonIconStyle() : ButtonIconStyle {
    return when(this) {
        1 -> ButtonIconStyle.None
        2 -> ButtonIconStyle.LeftIcon
        3 -> ButtonIconStyle.RightIcon
        else -> throw IllegalArgumentException("This value is not supported for ButtonIconStyle: $this")
    }
}

private fun Int.pxToDp(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()

private fun Float.pxToDp(): Float =
    this / Resources.getSystem().displayMetrics.density

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayoutCompat(context, attrs) {
    private var binding: LayoutCustomButtonBinding =
        LayoutCustomButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var buttonEnabled: Boolean
    private var colorFrom: Int? = null
    private var colorTo: Int? = null
    private var clickListener: OnClickListener? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0).apply {
            try {
                binding.customBtnText.text = getString(R.styleable.CustomButton_customButtonText)
                buttonEnabled = getBoolean(R.styleable.CustomButton_enabled, true)
                setButtonStyle(getInteger(R.styleable.CustomButton_customButtonStyle, 1).toButtonStyle())
                setButtonSize(getInteger(R.styleable.CustomButton_customButtonSize, 1).toButtonSize())
                setButtonIconStyle(getInteger(R.styleable.CustomButton_customButtonIconStyle, 1).toButtonIconStyle(), getResourceId(R.styleable.CustomButton_customButtonIconSrc, -1))
                setButtonAnimation()
            } finally {
                recycle()
            }
        }
    }

    private fun setButtonStyle(buttonStyle: ButtonStyle) {
        with(binding.customBtnRoot) {
            when(buttonStyle) {
                ButtonStyle.Primary -> {
                    background = AppCompatResources.getDrawable(context, R.drawable.radius_8_without_border)
                    if(buttonEnabled) {
                        background.setTint(context.getColor(R.color.custom_button_primary_normal))
                    } else {
                        background.setTint(context.getColor(R.color.custom_button_primary_alternative))
                    }
                    colorFrom = context.getColor(R.color.custom_button_primary_normal)
                    colorTo = context.getColor(R.color.custom_button_primary_support)
                    binding.customBtnText.setTextColor(context.getColor(R.color.white))
                }
                ButtonStyle.PrimaryLine -> {

                }
                ButtonStyle.GrayLine -> {

                }
                ButtonStyle.TextOnly -> {

                }
            }
        }
    }

    private fun setButtonSize(buttonSize: ButtonSize) {
        when(buttonSize) {
            ButtonSize.Large -> {
                binding.customBtnRoot.setPadding(24.pxToDp(), 14.pxToDp(), 24.pxToDp(), 14.pxToDp())
            }
            ButtonSize.Middle -> {
                binding.customBtnRoot.setPadding(20.pxToDp(), 12.pxToDp(), 20.pxToDp(), 12.pxToDp())
            }
            ButtonSize.Small -> {
                binding.customBtnRoot.setPadding(20.pxToDp(), 9.pxToDp(), 20.pxToDp(), 9.pxToDp())
            }
        }
    }

    private fun setButtonIconStyle(buttonIconStyle: ButtonIconStyle, drawableRes: Int = -1) {
        when(buttonIconStyle) {
            ButtonIconStyle.None -> {
                binding.customBtnLeftImage.visibility = View.GONE
                binding.customBtnRigntImage.visibility = View.GONE
            }
            ButtonIconStyle.LeftIcon -> {
                binding.customBtnLeftImage.apply {
                    visibility = View.VISIBLE
                    if(drawableRes != -1)
                        setImageDrawable(AppCompatResources.getDrawable(context, drawableRes))
                }
                binding.customBtnRigntImage.visibility = View.GONE
            }
            ButtonIconStyle.RightIcon -> {
                binding.customBtnLeftImage.visibility = View.GONE
                binding.customBtnRigntImage.apply {
                    visibility = View.VISIBLE
                    if(drawableRes != -1)
                        setImageDrawable(AppCompatResources.getDrawable(context, drawableRes))
                }
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setButtonAnimation() {
        if(buttonEnabled) {
            binding.customBtnRoot.setOnTouchListener { _, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startPressAnimation()
                        true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        resetAnimation()
                        clickListener?.onClick(this)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun startPressAnimation() {
        if(colorFrom != null && colorTo != null) {
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
            colorAnimation.duration = 300
            colorAnimation.addUpdateListener { animator ->
                binding.customBtnRoot.background.setTint(animator.animatedValue as Int)
            }
            colorAnimation.start()
        }
    }

    private fun resetAnimation() {
        if(colorFrom != null && colorTo != null) {
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorTo, colorFrom)
            colorAnimation.duration = 300
            colorAnimation.addUpdateListener { animator ->
                binding.customBtnRoot.background.setTint(animator.animatedValue as Int)
            }
            colorAnimation.start()
        }
    }
}