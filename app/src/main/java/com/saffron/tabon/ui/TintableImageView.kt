package com.saffron.tabon.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.saffron.tabon.R

open class TintableImageView : AppCompatImageView {
    private var mTint: ColorStateList? = null
    private var mBackgroundTint: ColorStateList? = null

    constructor(context: Context) : super(context) {}

    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TintableImageView, defStyle, 0)
        mTint = a.getColorStateList(R.styleable.TintableImageView_image_tint)
        mBackgroundTint = a.getColorStateList(R.styleable.TintableImageView_background_tint)
        a.recycle()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (mTint != null) {
            if (mTint!!.isStateful)
                setColorFilter(mTint!!.getColorForState(drawableState, 0))
            else
                setColorFilter(mTint!!)
        }
        val drawable = background
        if (mBackgroundTint != null && drawable != null) {
            var wrap = DrawableCompat.wrap(drawable)
            wrap = wrap.mutate()

            if (mBackgroundTint!!.isStateful)
                DrawableCompat.setTint(wrap, ContextCompat.getColor(context, mBackgroundTint!!.getColorForState(drawableState, 0)))
            else
                DrawableCompat.setTintList(wrap, mBackgroundTint)

            DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_IN)
        }
    }

    fun removeTint() {
        mTint = null
        clearColorFilter()
    }

    fun setColorFilter(tint: ColorStateList) {
        this.mTint = tint
        setColorFilter(tint.getColorForState(drawableState, 0))
    }
}