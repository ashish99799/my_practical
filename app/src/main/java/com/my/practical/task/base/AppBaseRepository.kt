package com.my.practical.task.base

import com.my.practical.task.model.api.ApiClient

abstract class AppBaseRepository {
    companion object {
        val api: ApiClient = ApiClient()
    }
}