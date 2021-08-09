package com.my.practical.task.model.repositorys

import com.my.practical.task.base.AppBaseRepository
import com.my.practical.task.model.responses.DataResponse
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import io.reactivex.Observable

// Repository
class AppRepository : AppBaseRepository() {

    fun getSearchUser(query: String, page: Int): Observable<DataResponse> {
        // Ratrofit API Calling
        return api.getUserSearch(query, page)
    }

    fun onUserInfo(query: String): Observable<UserData> {
        // Ratrofit API Calling
        return api.getUserInfo(query)
    }

    fun onUserRepo(query: String): Observable<List<UserRepoData>> {
        // Ratrofit API Calling
        return api.getUserRipo(query)
    }
}