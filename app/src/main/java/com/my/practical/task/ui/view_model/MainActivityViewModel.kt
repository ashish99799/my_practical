package com.my.practical.task.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.my.practical.task.model.api.ApiClient
import com.my.practical.task.model.repositorys.MainActivityRepository
import com.my.practical.task.model.responses.DataResponse
import com.my.practical.task.ui.listeners.MainActivityListener
import com.my.practical.task.util.checkInternetConnection
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Override ViewModel
class MainActivityViewModel : ViewModel() {

    // Over Activity Listener
    lateinit var mainActivityListener: MainActivityListener
    private var myCompositeDisposable: CompositeDisposable? = null
    var mainActivityRepository: MainActivityRepository = MainActivityRepository(ApiClient())

    lateinit var smartRefreshLayout: SmartRefreshLayout

    fun getSearchUser(context: Context, query: String, page: Int) {
        // Check Internet connectivity
        if (context.checkInternetConnection()) {
            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                mainActivityRepository.getSearchUser(query, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponse(response) }, { error -> onFailure(error) })
            )
        } else {
            // Internet is not connected
            if (smartRefreshLayout.isRefreshing) {
                smartRefreshLayout.finishRefresh()
            }
            if (smartRefreshLayout.isLoading) {
                smartRefreshLayout.finishLoadmore()
            }

            mainActivityListener.onFailure("Please check your internet connection!")
        }
    }

    private fun onResponse(response: DataResponse) {
        if (smartRefreshLayout.isRefreshing) {
            smartRefreshLayout.finishRefresh()
        }
        if (smartRefreshLayout.isLoading) {
            smartRefreshLayout.finishLoadmore()
        }
        mainActivityListener.onSuccess((response.items ?: arrayListOf()))
    }

    private fun onFailure(error: Throwable) {
        if (smartRefreshLayout.isRefreshing) {
            smartRefreshLayout.finishRefresh()
        }
        if (smartRefreshLayout.isLoading) {
            smartRefreshLayout.finishLoadmore()
        }
        mainActivityListener.onFailure("Fail ${error.message}")
    }
}