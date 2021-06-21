package com.my.practical.task.model.repositorys

import com.my.practical.task.model.api.ApiClient
import com.my.practical.task.model.responses.DataResponse
import io.reactivex.Observable

// Repository
class MainActivityRepository(val api: ApiClient) {
    fun getSearchUser(query: String, page: Int): Observable<DataResponse> {
        // Ratrofit API Calling
        return api.getUserSearch(query, page)
    }
}