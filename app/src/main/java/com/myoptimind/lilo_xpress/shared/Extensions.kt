package com.myoptimind.lilo_xpress.shared

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import java.net.UnknownHostException
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.android.synthetic.main.fragment_guest_logout_enter_pin.*
import okhttp3.MediaType
import okhttp3.RequestBody
import timber.log.Timber

fun String.toRequestBody(): RequestBody {
    return RequestBody.create(
        MediaType.parse("text/plain"), this)
}

fun List<String>.toRequestBody(): List<RequestBody> {
    return map { value ->
        RequestBody.create(
            MediaType.parse("text/plain"),
            value
        )
    }
}

fun ImageView.initLoading(context: Context) {
    Glide.with(context)
        .load(R.drawable.ripple_loading)
        .into(this)
}

fun Context.displayAlert(
    title: String,
    body: String,
    positiveText: String = "Ok"
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(body)
        .setPositiveButton(positiveText,null)
        .create()
        .show()
}



fun Context.displayGenericFormError(){
    AlertDialog.Builder(this)
        .setMessage(this.getString(R.string.message_error_fill_all))
        .setPositiveButton("Ok",null)
        .create()
        .show()
}