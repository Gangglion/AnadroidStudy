package com.example.practice_and.custom_button.component

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
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
    private var buttonStyle: ButtonStyle
    private var buttonIconStyle: ButtonIconStyle

    private var clickListener: OnClickListener? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0).apply {
            try {
                binding.customBtnText.text = getString(R.styleable.CustomButton_customButtonText)
                buttonEnabled = getBoolean(R.styleable.CustomButton_enabled, true)
                buttonStyle = getInteger(R.styleable.CustomButton_customButtonStyle, 1).toButtonStyle()
                setButtonStyle(buttonStyle)
                setButtonSize(getInteger(R.styleable.CustomButton_customButtonSize, 1).toButtonSize())
                buttonIconStyle = getInteger(R.styleable.CustomButton_customButtonIconStyle, 1).toButtonIconStyle()
                setButtonIconStyle(buttonIconStyle, getResourceId(R.styleable.CustomButton_customButtonIconSrc, -1))
                setButtonTextAndIcon(buttonStyle)
                setButtonAnimation()
            } finally {
                recycle()
            }
        }
    }

    private fun setButtonStyle(buttonStyle: ButtonStyle) {
        with(binding) {
            when(buttonStyle) {
                ButtonStyle.Primary -> {
                    with(customBtnRoot) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        if(buttonEnabled) {
                            background.setTint(context.getColor(R.color.custom_button_primary_default))
                        } else {
                            background.setTint(context.getColor(R.color.custom_button_primary_disabled))
                        }
                    }
                    with(customBtnSurface) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        if(buttonEnabled) {
                            background.setTint(context.getColor(R.color.custom_button_primary_default))
                        } else {
                            background.setTint(context.getColor(R.color.custom_button_primary_disabled))
                        }
                    }
                }
                ButtonStyle.PrimaryLine -> {
                    with(customBtnRoot) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        if(buttonEnabled) {
                            background.setTint(context.getColor(R.color.custom_button_primary_default))
                        } else {
                            background.setTint(context.getColor(R.color.custom_button_primary_disabled))
                        }
                    }
                    with(customBtnSurface) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        background.setTint(context.getColor(R.color.white))
                    }
                }
                ButtonStyle.GrayLine -> {
                    with(customBtnRoot) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        if(buttonEnabled) {
                            background.setTint(context.getColor(R.color.custom_button_gray_default))
                        } else {
                            background.setTint(context.getColor(R.color.custom_button_gray_disabled))
                        }
                    }
                    with(customBtnSurface) {
                        background = AppCompatResources.getDrawable(context, R.drawable.radius_8)
                        background.setTint(context.getColor(R.color.white))
                    }
                }
                ButtonStyle.TextOnly -> { }
            }
        }
    }

    private fun setButtonTextAndIcon(buttonStyle: ButtonStyle) {
        with(binding) {
            when(buttonStyle) {
                ButtonStyle.Primary -> {
                    customBtnText.setTextColor(context.getColor(R.color.white))
                    when(buttonIconStyle) {
                        ButtonIconStyle.LeftIcon -> {
                            customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))
                        }
                        ButtonIconStyle.RightIcon -> {
                            customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))
                        }
                        else -> {}
                    }
                }
                ButtonStyle.PrimaryLine -> {
                    if(buttonEnabled) {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_primary_default))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_default))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_default))
                            }
                            else -> {}
                        }
                    } else {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_primary_disabled))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_disabled))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_disabled))
                            }
                            else -> {}
                        }
                    }
                }
                ButtonStyle.GrayLine -> {
                    if(buttonEnabled) {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_gray_default))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_gray_default))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_gray_default))
                            }
                            else -> {}
                        }
                    } else {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_gray_disabled))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_gray_disabled))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_gray_disabled))
                            }
                            else -> {}
                        }
                    }
                }
                ButtonStyle.TextOnly -> {
                    if(buttonEnabled) {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_primary_default))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_default))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_primary_default))
                            }
                            else -> {

                            }
                        }
                    } else {
                        customBtnText.setTextColor(context.getColor(R.color.custom_button_only_text_disabled))
                        when(buttonIconStyle) {
                            ButtonIconStyle.LeftIcon -> {
                                customBtnLeftImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_only_text_disabled))
                            }
                            ButtonIconStyle.RightIcon -> {
                                customBtnRightImage.imageTintList = ColorStateList.valueOf(context.getColor(R.color.custom_button_only_text_disabled))
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setButtonSize(buttonSize: ButtonSize) {
        when(buttonSize) {
            ButtonSize.Large -> {
                binding.customBtnSurface.setPadding(24.pxToDp(), 14.pxToDp(), 24.pxToDp(), 14.pxToDp())
                binding.customBtnText.textSize = 18f
            }
            ButtonSize.Middle -> {
                binding.customBtnSurface.setPadding(20.pxToDp(), 12.pxToDp(), 20.pxToDp(), 12.pxToDp())
                binding.customBtnText.textSize = 16f
            }
            ButtonSize.Small -> {
                binding.customBtnSurface.setPadding(20.pxToDp(), 9.pxToDp(), 20.pxToDp(), 9.pxToDp())
                binding.customBtnText.textSize = 14f
            }
        }
    }

    private fun setButtonIconStyle(buttonIconStyle: ButtonIconStyle, drawableRes: Int = -1) {
        when(buttonIconStyle) {
            ButtonIconStyle.None -> {
                binding.customBtnLeftImage.visibility = View.GONE
                binding.customBtnRightImage.visibility = View.GONE
            }
            ButtonIconStyle.LeftIcon -> {
                binding.customBtnLeftImage.apply {
                    visibility = View.VISIBLE
                    if(drawableRes != -1)
                        setImageDrawable(AppCompatResources.getDrawable(context, drawableRes))
                }
                binding.customBtnRightImage.visibility = View.GONE
            }
            ButtonIconStyle.RightIcon -> {
                binding.customBtnLeftImage.visibility = View.GONE
                binding.customBtnRightImage.apply {
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
                        startPressAnimation(buttonStyle)
                        true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        resetAnimation(buttonStyle)
                        clickListener?.onClick(this)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun startPressAnimation(buttonStyle: ButtonStyle) {
        when(buttonStyle) {
            ButtonStyle.Primary -> {
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_default), context.getColor(R.color.custom_button_primary_pressed))
                colorAnimation.duration = 300
                colorAnimation.addUpdateListener { animator ->
                    binding.customBtnRoot.background.setTint(animator.animatedValue as Int)
                    binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                }
                colorAnimation.start()
            }
            ButtonStyle.PrimaryLine -> {
                val colorAnimBorder = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_default), context.getColor(R.color.custom_button_primary_pressed))
                colorAnimBorder.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnRoot.background.setTint(animator.animatedValue as Int)
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                val colorAnimSurface = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.white), context.getColor(R.color.custom_button_clicked))
                colorAnimSurface.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                    }
                }
                colorAnimBorder.start()
                colorAnimSurface.start()
            }
            ButtonStyle.GrayLine -> {
                val colorAnimBorder = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_gray_default), context.getColor(R.color.custom_button_gray_pressed))
                colorAnimBorder.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnRoot.background.setTint(animator.animatedValue as Int)
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                val colorAnimSurface = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.white), context.getColor(R.color.custom_button_gray_clicked))
                colorAnimSurface.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                    }
                }
                colorAnimBorder.start()
                colorAnimSurface.start()
            }
            ButtonStyle.TextOnly -> {
                val colorAnim = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_default), context.getColor(R.color.custom_button_primary_pressed))
                colorAnim.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                colorAnim.start()
            }
        }
    }

    private fun resetAnimation(buttonStyle: ButtonStyle) {
        when(buttonStyle) {
            ButtonStyle.Primary -> {
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_pressed), context.getColor(R.color.custom_button_primary_default))
                colorAnimation.duration = 300
                colorAnimation.addUpdateListener { animator ->
                    binding.customBtnRoot.background.setTint(animator.animatedValue as Int)
                    binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                }
                colorAnimation.start()
            }
            ButtonStyle.PrimaryLine -> {
                val colorAnimBorder = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_pressed), context.getColor(R.color.custom_button_primary_default))
                colorAnimBorder.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnRoot.background.setTint(animator.animatedValue as Int)
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                val colorAnimSurface = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_clicked), context.getColor(R.color.white))
                colorAnimSurface.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                    }
                }
                colorAnimBorder.start()
                colorAnimSurface.start()
            }
            ButtonStyle.GrayLine -> {
                val colorAnimBorder = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_gray_pressed), context.getColor(R.color.custom_button_gray_default))
                colorAnimBorder.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnRoot.background.setTint(animator.animatedValue as Int)
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                val colorAnimSurface = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_gray_clicked), context.getColor(R.color.white))
                colorAnimSurface.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        binding.customBtnSurface.background.setTint(animator.animatedValue as Int)
                    }
                }
                colorAnimBorder.start()
                colorAnimSurface.start()

            }
            ButtonStyle.TextOnly -> {
                val colorAnim = ValueAnimator.ofObject(ArgbEvaluator(), context.getColor(R.color.custom_button_primary_pressed), context.getColor(R.color.custom_button_primary_default))
                colorAnim.apply {
                    duration = 300
                    addUpdateListener { animator ->
                        with(binding) {
                            customBtnText.setTextColor(animator.animatedValue as Int)
                            when(buttonIconStyle) {
                                ButtonIconStyle.LeftIcon -> {
                                    customBtnLeftImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                ButtonIconStyle.RightIcon -> {
                                    customBtnRightImage.imageTintList = ColorStateList.valueOf(animator.animatedValue as Int)
                                }
                                else -> {}
                            }
                        }
                    }
                }
                colorAnim.start()
            }
        }
    }
}