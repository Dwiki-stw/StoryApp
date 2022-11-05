package com.example.storyapp.ui.customeview.editview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.BoringLayout
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class CustomEditText : AppCompatEditText {

    private lateinit var visibilityButton: Drawable
    private lateinit var visibilityOffButton: Drawable
    private lateinit var iconEmail: Drawable
    private lateinit var iconKey: Drawable
    private var isVisibility = false
    private var _isEmail: Boolean? = null
    private var _validPassword: Boolean? = null
    val isEmail = _isEmail
    val validPassword = _validPassword

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun init(){
        visibilityButton = ContextCompat.getDrawable(context, R.drawable.ic_visibility) as Drawable
        visibilityOffButton = ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable
        iconEmail = ContextCompat.getDrawable(context, R.drawable.ic_email) as Drawable
        iconKey = ContextCompat.getDrawable(context, R.drawable.ic_key) as Drawable


        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    when (inputType) {
                        1 ->  _isEmail = emailValidation(p0.toString())
                        129 -> _validPassword = passwordValidation(p0.toString())
                    }
                }
            }

        })

    }

    private fun showVisivilityButton(){
        if (isVisibility) {
            setButtonDrawables(endOfTheText = visibilityButton)
        }else{
            setButtonDrawables(endOfTheText = visibilityOffButton)
        }
    }

    private fun hideClearButton(){
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = when(inputType){
            1 -> iconEmail
            129 -> iconKey
            else -> null
        },
        endOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }



    private fun passwordValidation(text: String?): Boolean{
        val check = text?.length!! >= 6
        if (!check)
            error = "password less than 6"

        return check
    }

    private fun emailValidation(text: String?): Boolean{
        val check = Patterns.EMAIL_ADDRESS.matcher(text).matches()
        if(!check)
            error = "email invalid"

        return check
    }



}