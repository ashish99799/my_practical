package com.my.practical.task.view_model

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.my.practical.task.base.AppBaseViewModel
import com.my.practical.task.model.repositorys.AppRepository
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData

class GithubUserViewModel : AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context
        var appRepository: AppRepository = AppRepository()

        @JvmStatic
        fun newInstance(context: Context) = GithubUserViewModel().apply {
            mContext = context
        }
    }

    fun onUserInfo(query: String, responseBack: (UserData) -> Unit) {
        // API Calling
        callApi(mContext, appRepository.onUserInfo(query), true) {
            responseBack(it)
        }
    }

    fun onUserRepo(query: String, swipeRefreshLayout: SwipeRefreshLayout, responseBack: (List<UserRepoData>) -> Unit) {
        // API Calling
        callApi(mContext, appRepository.onUserRepo(query), swipeRefreshLayout) {
            responseBack(it)
        }
    }

}