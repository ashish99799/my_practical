package com.my.practical.task.model.repositorys

import com.my.practical.task.model.api.ApiClient
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import io.reactivex.Observable

// Repository
class GithubUserRepository(val api: ApiClient) {

    fun onUserInfo(query: String): Observable<UserData> {
        // Ratrofit API Calling
        return api.getUserInfo(query)
    }

    fun onUserRepo(query: String): Observable<List<UserRepoData>> {
        // Ratrofit API Calling
        return api.getUserRipo(query)
    }
}