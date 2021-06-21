package com.my.practical.task.ui.listeners

import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData

interface GithubUserListener {
    fun onSuccess(data: List<UserRepoData>)
    fun onSuccess(data: UserData)
    fun onFailure(message: String)
}