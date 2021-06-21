package com.my.practical.task.ui.listeners

import com.my.practical.task.model.responses.RowData

interface MainActivityListener {
    fun onSuccess(data: ArrayList<RowData>)
    fun onFailure(message: String)
}