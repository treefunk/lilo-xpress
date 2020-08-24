package com.myoptimind.lilo_xpress.shared

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    var mPattern: Pattern
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else return null
    }

    init {
        mPattern =
            Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    }
}