package com.my.practical.task.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class AppBaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T

    abstract fun setViewBinding(): T
    abstract fun initView()
    abstract fun initOnClick()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setViewBinding()
        setContentView(binding.root)
        initView()
        initOnClick()
    }

    fun hideKeyboard() {
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}