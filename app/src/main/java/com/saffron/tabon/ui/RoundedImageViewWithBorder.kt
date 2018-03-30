package com.saffron.tabon.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.widget.ImageView
import com.saffron.tabon.R
import java.io.FileNotFoundException

open class RoundedImageViewWithBorder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TintableImageView(context, attrs!!, defStyle) {
    private var mCornerRadius = DEFAULT_RADIUS
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mBorderColor: ColorStateList? = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR)
    private var mOval = false
    private var mRoundBackground = false
    private var mResource: Int = 0
    private var mDrawable: Drawable? = null
    private var mBackgroundDrawable: Drawable? = null
    private var mScaleType: ImageView.ScaleType? = null

    var cornerRadius: Int
        get() = mCornerRadius
        set(radius) {
            if (mCornerRadius == radius) {
                return
            }
            mCornerRadius = radius
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs()
        }

    var borderWidth: Int
        get() = mBorderWidth
        set(width) {
            if (mBorderWidth == width) {
                return
            }
            mBorderWidth = width
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs()
            invalidate()
        }

    open var borderColor: Int
        get() = if (mBorderColor != null)
            mBorderColor!!.defaultColor
        else
            RoundedDrawable.DEFAULT_BORDER_COLOR
        set(color) {
            borderColors = ColorStateList.valueOf(color)
        }

    var borderColors: ColorStateList?
        get() = mBorderColor
        set(colors) {
            if (mBorderColor == colors) {
                return
            }
            mBorderColor = colors ?: ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR)
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs()
            if (mBorderWidth > 0) {
                invalidate()
            }
        }

    var isOval: Boolean
        get() = mOval
        set(oval) {
            mOval = oval
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs()
            invalidate()
        }

    var isRoundBackground: Boolean
        get() = mRoundBackground
        set(roundBackground) {
            if (mRoundBackground == roundBackground) {
                return
            }
            mRoundBackground = roundBackground
            updateBackgroundDrawableAttrs()
            invalidate()
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0)
        val index = a.getInt(R.styleable.RoundedImageView_android_scaleType, -1)
        if (index >= 0) {
            scaleType = sScaleTypeArray[index]
        }
        mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_corner_radius, -1)
        mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundedImageView_border_width, -1)
        // don't allow negative values for radius and border
        if (mCornerRadius < 0) {
            mCornerRadius = DEFAULT_RADIUS
        }
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER_WIDTH
        }
        mBorderColor = a.getColorStateList(R.styleable.RoundedImageView_border_color)
        if (mBorderColor == null) {
            mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR)
        }
        mRoundBackground = a.getBoolean(R.styleable.RoundedImageView_round_background, false)
        mOval = a.getBoolean(R.styleable.RoundedImageView_is_oval, false)
        updateDrawableAttrs()
        updateBackgroundDrawableAttrs()
        a.recycle()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun getScaleType(): ImageView.ScaleType? {
        return mScaleType
    }

    override fun setScaleType(scaleType: ImageView.ScaleType?) {
        if (scaleType == null) {
            throw NullPointerException()
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType
            when (scaleType) {
                ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_END, ImageView.ScaleType.FIT_XY -> super.setScaleType(ImageView.ScaleType.FIT_XY)
                else -> super.setScaleType(scaleType)
            }
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs()
            invalidate()
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        mResource = 0
        mDrawable = RoundedDrawable.fromDrawable(drawable)
        updateDrawableAttrs()
        super.setImageDrawable(mDrawable)
    }

    override fun setImageBitmap(bm: Bitmap?) {
        mResource = 0
        mDrawable = RoundedDrawable.fromBitmap(bm)
        updateDrawableAttrs()
        super.setImageDrawable(mDrawable)
    }

    override fun setImageURI(uri: Uri?) {
        mResource = 0
        try {
            val inputStream = context.contentResolver.openInputStream(uri!!)
            mDrawable = Drawable.createFromStream(inputStream, uri.toString())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        mDrawable = RoundedDrawable.fromDrawable(mDrawable)
        updateDrawableAttrs()
        super.setImageDrawable(mDrawable)
    }

    override fun setImageResource(resId: Int) {
        if (mResource != resId) {
            mResource = resId
            mDrawable = resolveResource()
            updateDrawableAttrs()
            super.setImageDrawable(mDrawable)
        }
    }

    private fun resolveResource(): Drawable? {
        var d: Drawable? = null
        if (mResource != 0) {
            try {
                d = AppCompatResources.getDrawable(context, mResource)
            } catch (e: Exception) {
                mResource = 0
            }

        }
        return RoundedDrawable.fromDrawable(d)
    }

    override fun setBackground(background: Drawable) {
        setBackgroundDrawable(background)
    }

    private fun updateDrawableAttrs() {
        updateAttrs(mDrawable, false)
    }

    private fun updateBackgroundDrawableAttrs() {
        updateAttrs(mBackgroundDrawable, true)
    }

    private fun updateAttrs(drawable: Drawable?, background: Boolean) {
        if (drawable == null) {
            return
        }
        if (drawable is RoundedDrawable) {
            drawable
                    .setScaleType(mScaleType)
                    .setCornerRadius((if (background && !mRoundBackground) 0 else mCornerRadius).toFloat())
                    .setBorderWidth(mBorderWidth)
                    .setBorderColors(mBorderColor).isOval()
        } else if (drawable is LayerDrawable) {
            // loop through layers to and set drawable attrs
            val ld = drawable as LayerDrawable?
            val layers = ld!!.getNumberOfLayers()
            for (i in 0 until layers) {
                updateAttrs(ld.getDrawable(i), background)
            }
        }
    }

    override fun setBackgroundDrawable(background: Drawable) {
        mBackgroundDrawable = RoundedDrawable.fromDrawable(background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            super.setBackground(mBackgroundDrawable)
        else
            super.setBackgroundDrawable(mBackgroundDrawable)
    }

    companion object {
        val DEFAULT_RADIUS = 0
        val DEFAULT_BORDER_WIDTH = 0
        private val sScaleTypeArray = arrayOf(ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE)
    }
}