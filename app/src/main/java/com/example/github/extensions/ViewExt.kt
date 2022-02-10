package com.example.github.extensions

import android.os.Build
import android.text.InputFilter
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

fun View.show() {
    visibility = VISIBLE
}

fun View.hide(conserveSpace: Boolean = false) {
    visibility = if (conserveSpace) INVISIBLE else GONE
}

fun View.setVisibilityState(state: Boolean) {
    if (state) show() else hide()
}

fun ViewGroup.showViewWithChildren() {
    show()
    for (view in children) {
        view.show()
    }
}

fun RecyclerView.onScrollChanged(scrollListener: (Int) -> Unit) =
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            scrollListener(computeVerticalScrollOffset())
        }
    })

fun NestedScrollView.onScrollChanged(scrollListener: (Int) -> Unit) =
    setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        scrollListener(scrollY)
    })

// https://stackoverflow.com/a/10808454/10640760
@RequiresApi(Build.VERSION_CODES.M)
fun ExpandableListView.onScrollChanged(scrollListener: (Int) -> Unit) =
    setOnScrollChangeListener { _, _, _, _, _ ->
        getChildAt(0)?.let {
            scrollListener(it.top * -1 + firstVisiblePosition * it.height)
        }
    }

fun View.setViewPadding(@DimenRes topBottomPaddingRes: Int, @DimenRes leftRightPaddingRes: Int) {
    val leftRightPadding = context.resources.getDimension(leftRightPaddingRes).toInt()
    val topBottomPadding = context.resources.getDimension(topBottomPaddingRes).toInt()
    setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding)
}

fun EditText.setMaxLength(length: Int) {
    this.filters = arrayOf(InputFilter.LengthFilter(length))
}


fun View.fadeOut(duration: Long = 150) {
    animate().alpha(0f).setDuration(duration).setInterpolator(DecelerateInterpolator()).start()
}

fun View.fadeIn(duration: Long = 150) {
    animate().alpha(1f).setDuration(duration).setInterpolator(AccelerateInterpolator()).start()
}

fun View.disable() { isEnabled = false }

fun View.enable() { isEnabled = true }

fun ImageView.enable() {
    (this as View).enable()
    alpha = 1.0f
    isFocusable = true
    isClickable = true
    imageTintList = null
}

fun ImageView.setEnabledState(state: Boolean) {
    if (state) enable() else disable()
}
