package com.rbc.yelp.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.rbc.yelp.R

class ClearableEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleArr: Int = View.NO_ID
) : RelativeLayout(context, attrs, defStyleArr) {
    init {
        initView(attrs)
    }

    private lateinit var startIcon: ImageView
    private lateinit var clearIcon: ImageView
    private lateinit var editText: EditText

    private fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.common_form_input_field, this)
        initUi()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText)
        try {
            val hintText = typedArray.getString(R.styleable.ClearableEditText_hint)
            val startIconDrawable =
                typedArray.getResourceId(R.styleable.ClearableEditText_startIcon, View.NO_ID)
            val endIconDrawable =
                typedArray.getResourceId(R.styleable.ClearableEditText_clearIcon, View.NO_ID)
            setClearIcon(endIconDrawable)
            setLeftIcon(startIconDrawable)
            startIcon.contentDescription = null
            clearIcon.contentDescription = context.resources.getString(R.string.clear)

            setHint(hintText)
        } finally {
            typedArray.recycle()
        }
    }

    fun addEditTextWatcher() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.let {
                    clearIcon.visibility =
                        if (it.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setClearIconClickListener() {
        clearIcon.setOnClickListener {
            clear()
        }
    }

    private fun initUi() {
        startIcon = findViewById(R.id.startIcon)
        clearIcon = findViewById(R.id.clearIcon)
        editText = findViewById(R.id.inputEditText)
        addEditTextWatcher()
    }

    fun setHint(text: String?) {
        editText.hint = text
    }

    fun setClearIcon(@DrawableRes resId: Int) {
        getDrawable(resId)?.let {
            clearIcon.setImageDrawable(it)
        }
        setClearIconClickListener()
    }

    fun setLeftIcon(@DrawableRes resId: Int) {
        getDrawable(resId)?.let {
            startIcon.setImageDrawable(it)
            startIcon.visibility = View.VISIBLE
        } ?: kotlin.run {
            startIcon.visibility = View.GONE
        }

    }

    fun clear() {
        editText.text?.clear()
    }

    private fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return if (resId != View.NO_ID) {
            ContextCompat.getDrawable(context, resId)
        } else {
            null
        }
    }

    fun isNotEmpty(): Boolean =
        !TextUtils.isEmpty(editText.text?.toString())

    fun getText(): String = editText.text?.toString() ?: ""

    fun setText(text:String){
        editText.setText(text)
    }
}