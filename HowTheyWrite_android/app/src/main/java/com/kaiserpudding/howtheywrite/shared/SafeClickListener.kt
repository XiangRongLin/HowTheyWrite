package com.kaiserpudding.howtheywrite.shared

import android.os.SystemClock
import android.view.View

/**
 *Custom [View.OnClickListener] which ignores clicks which happened while inside the timeout
 * of the last click
 *
 * @property timeout The timeout, default is 1000
 * @property onSafeClick The action that should be executed when the view was clicked
 */
class SafeClickListener(
        private val timeout: Int = 1000,
        private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked >= timeout) {
            lastTimeClicked = SystemClock.elapsedRealtime()
            onSafeClick(v)
        }
    }
}


fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}