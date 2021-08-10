package com.my.practical.task.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.my.practical.task.R
import com.my.practical.task.model.responses.ResponseCode
import com.my.practical.task.pref.AppPref.clearPref
import com.my.practical.task.view.main.search.SearchActivity
import com.my.practical.task.util.checkInternetConnection
import com.my.practical.task.util.hideDialog
import com.my.practical.task.util.showDialog
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.json.JSONObject
import retrofit2.HttpException

abstract class AppBaseViewModel : ViewModel() {

    private var context: Context? = null

    // T => Template type
    fun <T> callApi(
        mContext: Context,
        observable: Observable<T>,
        showLoader: Boolean,
        responseBlock: (T) -> Unit
    ) {
        context = mContext
        // Check Internet connectivity
        if (!mContext.checkInternetConnection()) {
            return
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    if (showLoader) {
                        context!!.showDialog()
                    }
                }

                override fun onNext(response: T) {
                    Log.e("API", "Success : ${Gson().toJson(response)}")
                    responseBlock(response)
                }

                override fun onError(e: Throwable) {
                    if (showLoader) {
                        context!!.hideDialog()
                    }

                    onResponseFailure(context!!, e)
                }

                override fun onComplete() {
                    if (showLoader) {
                        mContext.hideDialog()
                    }
                }
            })
    }

    fun <T> callApi(
        mContext: Context,
        observable: Observable<T>,
        smartRefreshLayout: SmartRefreshLayout,
        responseBlock: (T) -> Unit
    ) {
        context = mContext

        // Check Internet connectivity
        if (!mContext.checkInternetConnection()) {
            if (smartRefreshLayout.isLoading) {
                smartRefreshLayout.finishLoadmore()
            }
            return
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(response: T) {
                    Log.e("API", "Success : ${Gson().toJson(response)}")
                    responseBlock(response)
                }

                override fun onError(e: Throwable) {
                    if (smartRefreshLayout.isLoading) {
                        smartRefreshLayout.finishLoadmore()
                    }

                    onResponseFailure(context!!, e)
                }

                override fun onComplete() {
                    if (smartRefreshLayout.isLoading) {
                        smartRefreshLayout.finishLoadmore()
                    }
                }
            })
    }

    fun <T> callApi(
        mContext: Context,
        observable: Observable<T>,
        swipeRefreshLayout: SwipeRefreshLayout,
        responseBlock: (T) -> Unit
    ) {
        context = mContext

        // Check Internet connectivity
        if (!mContext.checkInternetConnection()) {
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            }
            return
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(response: T) {
                    Log.e("API", "Success : ${Gson().toJson(response)}")
                    responseBlock(response)
                }

                override fun onError(e: Throwable) {
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.isRefreshing = false
                    }
                    onResponseFailure(context!!, e)
                }

                override fun onComplete() {
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            })
    }

    fun onResponseFailure(mContext: Context, throwable: Throwable) {
        context = mContext
        val error: HttpException = throwable as HttpException
        Log.e("API", "Error : ${error.message ?: ""}")
        Log.e("API", "Error : ${error.code()}")
        handleResponseError(error)
    }

    private fun handleResponseError(throwable: HttpException) {
        when (throwable.code()) {
            ResponseCode.InValidateData.code -> {
                val errorRawData = throwable.response()!!.errorBody().toString()
                if (errorRawData.isNotEmpty()) {
                    if (errorRawData.contains("{") && errorRawData.contains("msg")) {
                        errorDialog(
                            JSONObject(errorRawData).getString("msg"),
                            context!!.getString(R.string.alert)
                        )
                    } else {
                        errorDialog(errorRawData)
                    }
                }
            }
            ResponseCode.Unauthenticated.code -> {
                val errorRawData = throwable.message
                if (!errorRawData.isNullOrEmpty()) {
                    if (errorRawData.contains("{") && errorRawData.contains("msg")) {
                        context!!.alert(JSONObject(errorRawData).getString("msg"), context!!.getString(R.string.alert)) {
                            okButton { onAuthFail() }
                        }.show()
                    } else {
                        errorDialog(errorRawData.toString())
                    }
                } else {
                    onAuthFail()
                }
            }
            ResponseCode.ForceUpdate.code -> {

            }
            ResponseCode.ServerError.code -> errorDialog(context!!.getString(R.string.internal_server_error))
            ResponseCode.NotFound.code -> errorDialog(context!!.getString(R.string.page_not_found))
            ResponseCode.BadRequest.code,
            ResponseCode.Unauthorized.code,
            ResponseCode.RequestTimeOut.code,
            ResponseCode.Conflict.code,
            ResponseCode.Blocked.code -> {
                val errorRawData = throwable.message
                if (!errorRawData.isNullOrEmpty()) {
                    if (errorRawData.contains("{") && errorRawData.contains("msg")) {
                        errorDialog(JSONObject(errorRawData).getString("msg"))
                    } else {
                        errorDialog(errorRawData)
                    }
                }
            }
        }
    }

    private fun errorDialog(optString: String, title: String = context!!.getString(R.string.app_name)) {
//        toastError(optString)
        context!!.alert(optString, title) { okButton { } }.build().show()
    }

    private fun toastError(message: String) {
        context!!.toast(message)
    }

    private fun onAuthFail() {
        clearPref()
        context!!.startActivity(context!!.intentFor<SearchActivity>().clearTask().newTask())
    }

}