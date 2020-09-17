package com.myoptimind.lilo_xpress.shared

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView



class NestedScrollViewNonFocus : NestedScrollView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect): Int {
        return 0
    }
}