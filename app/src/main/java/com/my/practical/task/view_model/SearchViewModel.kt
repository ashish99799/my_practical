package com.my.practical.task.view_model

import android.content.Context
import com.my.practical.task.base.AppBaseViewModel
import com.my.practical.task.model.repositorys.AppRepository
import com.my.practical.task.model.responses.RowData
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class SearchViewModel : AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context
        var appRepository: AppRepository = AppRepository()

        @JvmStatic
        fun newInstance(context: Context) = SearchViewModel().apply {
            mContext = context
        }
    }

    fun getSearchUser(
        query: String, page: Int,
        smartRefreshLayout: SmartRefreshLayout,
        responseBlock: (ArrayList<RowData>) -> Unit
    ) {
        // API Calling
        callApi(mContext, appRepository.getSearchUser(query, page), smartRefreshLayout) {
            responseBlock((it.items ?: arrayListOf()))
        }
    }

}