package com.my.practical.task.ui.view.main

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseActivity
import com.my.practical.task.databinding.ActivityMainBinding
import com.my.practical.task.ui.view.github_user.GithubUser
import com.my.practical.task.ui.view.main.adapter.GithubUserAdapter
import com.my.practical.task.ui.view_model.MainActivityViewModel
import com.my.practical.task.util.INTENT_DATA
import com.my.practical.task.util.checkBackPress
import com.my.practical.task.util.doubleBackToExitPressedOnce
import com.my.practical.task.wegates.RefreshLayoutHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import org.jetbrains.anko.startActivity

class MainActivity : AppBaseActivity<ActivityMainBinding, MainActivityViewModel>(), OnLoadmoreListener, SearchView.OnQueryTextListener, View.OnClickListener {

    var query: String = ""
    var page: Int = 1
    lateinit var githubUserAdapter: GithubUserAdapter

    // View Binding
    override fun setViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    // View Model
    override fun setViewModel() = MainActivityViewModel.newInstance(this)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        githubUserAdapter = GithubUserAdapter()
        binding.rvGithubUsers.apply {
            adapter = githubUserAdapter.also {
                it.addAll(arrayListOf())
            }
        }

        binding.searchView.setOnQueryTextListener(this)
        (binding.searchView.findViewById(androidx.appcompat.R.id.search_plate) as View).setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite))

        binding.smartRefresh.isEnableOverScrollBounce = true
        binding.smartRefresh.isEnableRefresh = false
        RefreshLayoutHelper.initToLoadMoreStyle(binding.smartRefresh, this)
    }

    override fun initOnClick() {
        (binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView).setOnClickListener(this)
        githubUserAdapter.setItemClickListener { view, position, data ->
            startActivity<GithubUser>(INTENT_DATA to data)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.search_close_btn -> {
                binding.searchView.clearFocus()
                binding.searchView.setQuery("", false)
                query = ""
                page = 1
                githubUserAdapter.clearAll()
            }
        }
    }

    override fun onLoadmore(refreshlayout: RefreshLayout) {
        getSearchUser()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) {
            return false
        }

        query = newText.toString()
        page = 1
        getSearchUser()
        return false
    }

    private fun getSearchUser() {
        viewModel.getSearchUser(query, page, binding.smartRefresh) {
            if (page == 1) {
                githubUserAdapter.addAll(it)
            } else {
                githubUserAdapter.appendAll(it)
                page++
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        checkBackPress()
    }

}