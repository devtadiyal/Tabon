package com.saffron.tabon.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import com.saffron.tabon.R

class MaterialLetterIcon @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RoundedImageViewWithBorder(context, attrs, defStyle) {
    private var mBuilder: TextDrawable.IShapeBuilder? = null
    private lateinit var mText: String
    private var mTextSize: Int = 0
    private var mTextColor: Int = 0
    private var mShapeColor: Int = 0
    var letterCount: Int = 0
    var typeFace: Typeface? = null
        private set
    var isBold: Boolean = false
    var isUppercase: Boolean = false
    private val mOval: Boolean = false

    var text: String?
        get() = mText
        set(text) {
            computeText(text)
            updateDrawable()
        }

    var textSize: Int
        get() = mTextSize
        set(textSize) {
            mTextSize = textSize
            updateDrawable()
        }

    var shapeColor: Int
        get() = mShapeColor
        set(@ColorInt color) {
            mShapeColor = color
            updateDrawable()
        }

    var textColor: Int
        get() = mTextColor
        set(@ColorInt color) {
            mTextColor = color
            updateDrawable()
        }

    override var borderColor: Int
        get() = super.borderColor
        set(color) {
            borderColors = ColorStateList.valueOf(color)
        }

    init {
        mBuilder = TextDrawable.builder()
        val attr = context.obtainStyledAttributes(attrs, R.styleable.MaterialLetterIcon, defStyle, 0)
        letterCount = attr.getInt(R.styleable.MaterialLetterIcon_text_number, 2)
        mTextSize = attr.getInt(R.styleable.MaterialLetterIcon_text_size, 26)
        mTextColor = attr.getColor(R.styleable.MaterialLetterIcon_text_color, Color.WHITE)
        mShapeColor = attr.getColor(R.styleable.MaterialLetterIcon_shape_color, Color.RED)
        isBold = attr.getBoolean(R.styleable.MaterialLetterIcon_is_bold, false)
        isUppercase = attr.getBoolean(R.styleable.MaterialLetterIcon_is_uppercase, false)

        computeText(attr.getString(R.styleable.MaterialLetterIcon_text))
        attr.recycle()
        updateDrawable()
    }

    private fun toPx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

    private fun updateDrawable() {
        if (TextUtils.isEmpty(mText)) {
            return
        }
        val iConfigBuilder = mBuilder!!.beginConfig().withBorder(borderWidth).textColor(mTextColor).borderColor(borderColor)
        if (typeFace != null)
            iConfigBuilder.useFont(typeFace!!)
        if (mTextSize > 0)
            iConfigBuilder.fontSize(toPx(mTextSize))
        if (isBold)
            iConfigBuilder.bold()
        if (isUppercase)
            iConfigBuilder.toUpperCase()
        mBuilder = iConfigBuilder.endConfig()
        val mDrawable: Drawable
        if (isOval)
            mDrawable = mBuilder!!.buildRound(mText, mShapeColor)
        else if (cornerRadius > 0)
            mDrawable = mBuilder!!.buildRoundRect(mText, mShapeColor, cornerRadius / 5)
        else
            mDrawable = mBuilder!!.buildRect(mText, mShapeColor)
        setImageDrawable(mDrawable)
    }

    private fun computeText(text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        val initials = text!!.split("\\s+".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val initialsPlain = StringBuilder(letterCount)
        for (initial in initials) {
            if (!initial.isEmpty())
                initialsPlain.append(initial.substring(0, 1))
        }
        mText = initialsPlain.toString()
        mText = mText!!.substring(0, if (letterCount > mText!!.length) mText!!.length else letterCount)
    }

    fun setTypeface(typeface: Typeface) {
        typeFace = typeface
        updateDrawable()
    }
}