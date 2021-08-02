package com.tawk.to.ktx

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}


fun View.visibleIfOrGone(status: Boolean) {
    if (status)
        this.visible()
    else
        this.gone()
}

fun View.goneIfOrVisible(status: Boolean) {
    if (status)
        this.gone()
    else
        this.visible()
}

fun View.visibleIfOrInvisible(status: Boolean) {
    if (status)
        this.visible()
    else
        this.inVisible()
}

fun View.lazyExecute(task: () -> Unit) {
    this.postDelayed({
        task.invoke()
    }, 100)
}