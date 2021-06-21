package com.my.practical.task.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.my.practical.task.R
import com.my.practical.task.databinding.ProgressBinding
import org.jetbrains.anko.toast

const val INTENT_DATA = "INTENT_DATA"

internal var SpinKitProgressDialog: Dialog? = null

var doubleBackToExitPressedOnce = false

fun Context.checkBackPress() {
    doubleBackToExitPressedOnce = true
    toast(getString(R.string.please_click_back_again_to_exit))
    Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
}

fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return (activeNetwork != null && activeNetwork.isConnected)
}

fun Context.checkInternetConnection(): Boolean {
    return if (isNetworkConnectionAvailable()) {
        Log.d("Network", "Connected")
        true
    } else {
        Log.d("Network", "Not Connected")
        checkNetworkConnectionDialog()
        false
    }
}

fun Context.checkNetworkConnectionDialog() {
    val builder = AlertDialog.Builder(this, R.style.DialogTheme)
    builder.setTitle(resources.getString(R.string.no_connection))
    builder.setMessage(resources.getString(R.string.turn_on_connection))
    builder.setNegativeButton(getString(R.string.dialog_ok)) { dialog, which -> dialog.dismiss() }
    val alertDialog = builder.create()
    alertDialog.show()
}

fun Context.showDialog() {
    // implementation 'com.github.ybq:Android-SpinKit:1.2.0'
    if (SpinKitProgressDialog != null) {
        if (SpinKitProgressDialog!!.isShowing) {
            return
        }
    }

    SpinKitProgressDialog = Dialog(this)
    SpinKitProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
    SpinKitProgressDialog!!.window!!
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    SpinKitProgressDialog!!.setContentView(ProgressBinding.inflate(LayoutInflater.from(this)).root)
    SpinKitProgressDialog!!.window!!
        .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    SpinKitProgressDialog!!.window!!.setDimAmount(0.3f)
    SpinKitProgressDialog!!.setCancelable(false)
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(SpinKitProgressDialog!!.window!!.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    SpinKitProgressDialog!!.show()
    SpinKitProgressDialog!!.window!!.attributes = lp
}

fun Context.hideDialog() {
    if (SpinKitProgressDialog != null && SpinKitProgressDialog!!.isShowing) {
        SpinKitProgressDialog!!.dismiss()
        SpinKitProgressDialog = null
    }
}

fun ImageView.loadImage(imgUrl: Any) {
    Glide.with(this)
        .load(imgUrl)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .into(this)
}