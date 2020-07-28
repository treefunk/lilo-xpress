package com.myoptimind.lilo_xpress.shared

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import java.net.UnknownHostException
import com.myoptimind.lilo_xpress.data.Result
import okhttp3.MediaType
import okhttp3.RequestBody
import timber.log.Timber

fun String.toRequestBody(): RequestBody {
    return RequestBody.create(
        MediaType.parse("text/plain"), this)
}
