package com.my.practical.task.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.my.practical.task.model.api.ApiClient
import com.my.practical.task.model.repositorys.GithubUserRepository
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import com.my.practical.task.ui.listeners.GithubUserListener
import com.my.practical.task.util.checkInternetConnection
import com.my.practical.task.util.hideDialog
import com.my.practical.task.util.showDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Override ViewModel
class GithubUserViewModel : ViewModel() {

    // Over Activity Listener
    lateinit var githubUserListener: GithubUserListener
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var myCompositeDisposable: CompositeDisposable? = null

    var githubUserRepository = GithubUserRepository(ApiClient())

    fun onUserInfo(context: Context, query: String) {
        // Check Internet connectivity
        if (context.checkInternetConnection()) {
            // API Calling Start
            context.showDialog()
            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                githubUserRepository.onUserInfo(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { response -> onResponse(context, response) },
                        { error -> onFailure(context, error) }
                    )
            )
        } else {
            // Internet is not connected
            context.hideDialog()
            githubUserListener.onFailure("Please check your internet connection!")
        }
    }

    fun onUserRepo(context: Context, query: String) {
        // Check Internet connectivity
        if (context.checkInternetConnection()) {
            // API Calling Start
            swipeRefreshLayout.isRefreshing = true

            // Ratrofit API Calling
            myCompositeDisposable = CompositeDisposable()
            myCompositeDisposable?.add(
                githubUserRepository.onUserRepo(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponseList(response) }, { t -> onFailure(context, t) })
            )
        } else {
            // Internet is not connected
            swipeRefreshLayout.isRefreshing = false
            githubUserListener.onFailure("Please check your internet connection!")
        }
    }

    private fun onResponse(context: Context, response: UserData) {
        context.hideDialog()
        githubUserListener.onSuccess(response)
    }

    private fun onResponseList(response: List<UserRepoData>) {
        swipeRefreshLayout.isRefreshing = false
        githubUserListener.onSuccess(response)
    }

    private fun onFailure(context: Context, error: Throwable) {
        context.hideDialog()
        swipeRefreshLayout.isRefreshing = false
        githubUserListener.onFailure("Fail ${error.message}")
    }

}